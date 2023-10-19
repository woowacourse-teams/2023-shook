package shook.shook.member_part.application;

import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.member_part.application.dto.MemberPartRegisterRequest;
import shook.shook.member_part.domain.MemberPart;
import shook.shook.member_part.domain.repository.MemberPartRepository;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException.SongNotExistException;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MemberPartService {

    private final SongRepository songRepository;
    private final MemberRepository memberRepository;
    private final MemberPartRepository memberPartRepository;

    @Transactional
    public void register(final Long songId, final Long memberId, final MemberPartRegisterRequest request) {
        final Song song = getSong(songId);
        final Member member = getMember(memberId);
        validateAlreadyExistMemberPart(song, member);

        final MemberPart memberPart = MemberPart.forSave(request.getStartSecond(), request.getLength(), song, member);
        memberPartRepository.save(memberPart);
    }

    private void validateAlreadyExistMemberPart(final Song song, final Member member) {
        final boolean existsMemberPart = memberPartRepository.existsByMemberAndSong(member, song);

        if (existsMemberPart) {
            throw new MemberException.MemberPartAlreadyExistException(
                Map.of("songId", String.valueOf(song.getId()),
                       "memberId", String.valueOf(member.getId()))
            );
        }
    }

    private Member getMember(final Long memberId) {
        return memberRepository.findById(memberId)
            .orElseThrow(() -> new MemberException.MemberNotExistException(
                Map.of("memberId", String.valueOf(memberId))
            ));
    }

    private Song getSong(final Long songId) {
        return songRepository.findById(songId)
            .orElseThrow(() -> new SongNotExistException(
                Map.of("songId", String.valueOf(songId))
            ));
    }

    @Transactional
    public void delete(final Long memberId, final Long memberPartId) {
        final MemberPart memberPart = memberPartRepository.findByMemberIdAndId(memberId, memberPartId)
            .orElseThrow(() -> new AuthorizationException.UnauthenticatedException(
                Map.of("memberId", String.valueOf(memberId), "memberPartId", String.valueOf(memberPartId))
            ));

        memberPartRepository.delete(memberPart);
    }
}
