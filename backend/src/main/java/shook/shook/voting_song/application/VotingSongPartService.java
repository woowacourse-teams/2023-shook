package shook.shook.voting_song.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException.MemberNotExistException;
import shook.shook.part.domain.PartLength;
import shook.shook.voting_song.application.dto.VotingSongPartRegisterRequest;
import shook.shook.voting_song.domain.Vote;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.VotingSongPart;
import shook.shook.voting_song.domain.repository.VoteRepository;
import shook.shook.voting_song.domain.repository.VotingSongPartRepository;
import shook.shook.voting_song.domain.repository.VotingSongRepository;
import shook.shook.voting_song.exception.VotingSongException;
import shook.shook.voting_song.exception.VotingSongPartException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class VotingSongPartService {

    private final MemberRepository memberRepository;
    private final VotingSongRepository votingSongRepository;
    private final VotingSongPartRepository votingSongPartRepository;
    private final VoteRepository voteRepository;

    @Transactional
    public boolean registerAndReturnMemberPartDuplication(final MemberInfo memberInfo,
                                                          final Long votingSongId,
                                                          final VotingSongPartRegisterRequest request) {
        final long memberId = memberInfo.getMemberId();
        final Member member = findMemberThrowIfNotExist(memberId);
        final VotingSong votingSong = findVotingSongThrowIfNotExist(votingSongId);
        final int startSecond = request.getStartSecond();
        final PartLength partLength = PartLength.findBySecond(request.getLength());
        final VotingSongPart votingSongPart = VotingSongPart.forSave(member, startSecond, partLength, votingSong);

        if (isMemberSamePartExist(votingSongPart)) {
            return true;
        }
        votePart(votingSong, votingSongPart);
        return false;
    }

    private Member findMemberThrowIfNotExist(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberNotExistException::new);
    }

    private VotingSong findVotingSongThrowIfNotExist(final Long votingSongId) {
        return votingSongRepository.findById(votingSongId)
            .orElseThrow(() -> {
                final Map<String, String> errorProperties = Map.of("VotingSongId", String.valueOf(votingSongId));
                return new VotingSongException.VotingSongNotExistException(errorProperties);
            });
    }

    private boolean isMemberSamePartExist(final VotingSongPart votingSongPart) {
        return votingSongPartRepository.existsByVotingSongAndMemberAndStartSecondAndLength(
            votingSongPart.getVotingSong(),
            votingSongPart.getMember(),
            votingSongPart.getStartSecond(),
            votingSongPart.getLength()
        );
    }

    private void votePart(final VotingSong votingSong, final VotingSongPart votingSongPart) {
        if (votingSong.isUniquePart(votingSongPart)) {
            addPartAndVote(votingSong, votingSongPart);
            return;
        }
        voteToExistPart(votingSong, votingSongPart);
    }

    private void addPartAndVote(final VotingSong votingSong, final VotingSongPart votingSongPart) {
        votingSong.addPart(votingSongPart);
        votingSongPartRepository.save(votingSongPart);

        voteToPart(votingSongPart);
    }

    private void voteToExistPart(final VotingSong votingSong, final VotingSongPart votingSongPart) {
        final VotingSongPart existPart = votingSong.getSameLengthPartStartAt(votingSongPart)
            .orElseThrow(() -> new VotingSongPartException.PartNotExistException(
                Map.of(
                    "VotingSongId", String.valueOf(votingSong.getId()),
                    "StartSecond", String.valueOf(votingSongPart.getStartSecond()),
                    "PartLength", votingSongPart.getLength().name()
                )
            ));

        voteToPart(existPart);
    }

    private void voteToPart(final VotingSongPart votingSongPart) {
        final Vote newVote = Vote.forSave(votingSongPart);
        votingSongPart.vote(newVote);
        voteRepository.save(newVote);
    }
}
