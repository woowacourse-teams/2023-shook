package shook.shook.voting_song.application;

import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
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

        final Optional<VotingSongPart> findVotingSongPart =
            votingSongPartRepository.findByVotingSongAndStartSecondAndLength(votingSong, startSecond, partLength);

        if (findVotingSongPart.isPresent()) {
            return voteToExistVotingSongPartAndReturnVoteDuplication(member, votingSong, findVotingSongPart.get());
        }

        final VotingSongPart newVotingSongPart = VotingSongPart.forSave(startSecond, partLength, votingSong);
        addPartAndVote(member, votingSong, newVotingSongPart);
        return false;
    }

    private Member findMemberThrowIfNotExist(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(MemberException.MemberNotExistException::new);
    }

    private VotingSong findVotingSongThrowIfNotExist(final Long votingSongId) {
        return votingSongRepository.findById(votingSongId)
            .orElseThrow(() -> {
                final Map<String, String> errorProperties = Map.of("VotingSongId", String.valueOf(votingSongId));
                return new VotingSongException.VotingSongNotExistException(errorProperties);
            });
    }

    private boolean voteToExistVotingSongPartAndReturnVoteDuplication(final Member member,
                                                                      final VotingSong votingSong,
                                                                      final VotingSongPart votingSongPart) {
        if (existSameVoteByMember(member, votingSongPart)) {
            return true;
        }
        voteToExistPart(member, votingSong, votingSongPart);
        return false;
    }

    private boolean existSameVoteByMember(final Member member, final VotingSongPart votingSongPart) {
        return voteRepository.existsByMemberAndVotingSongPart(member, votingSongPart);
    }

    private void voteToExistPart(final Member member,
                                 final VotingSong votingSong,
                                 final VotingSongPart votingSongPart) {
        final VotingSongPart existPart = votingSong.getSameLengthPartStartAt(votingSongPart)
            .orElseThrow(() -> new VotingSongPartException.PartNotExistException(
                Map.of(
                    "VotingSongId", String.valueOf(votingSong.getId()),
                    "StartSecond", String.valueOf(votingSongPart.getStartSecond()),
                    "PartLength", votingSongPart.getLength().name()
                )
            ));

        voteToPart(member, existPart);
    }

    private void voteToPart(final Member member, final VotingSongPart votingSongPart) {
        final Vote newVote = Vote.forSave(member, votingSongPart);
        votingSongPart.vote(newVote);
        voteRepository.save(newVote);
    }

    private void addPartAndVote(final Member member, final VotingSong votingSong, final VotingSongPart votingSongPart) {
        votingSong.addPart(votingSongPart);
        votingSongPartRepository.save(votingSongPart);

        voteToPart(member, votingSongPart);
    }
}
