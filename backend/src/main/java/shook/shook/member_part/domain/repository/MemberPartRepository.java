package shook.shook.member_part.domain.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shook.shook.member.domain.Member;
import shook.shook.member_part.domain.MemberPart;
import shook.shook.member_part.domain.repository.dto.SongMemberPartCreatedAtDto;
import shook.shook.song.domain.Song;

public interface MemberPartRepository extends JpaRepository<MemberPart, Long> {

    Optional<MemberPart> findByMemberIdAndId(final Long memberId, final Long memberPartId);

    List<MemberPart> findByMemberAndSongIdIn(final Member member, final List<Long> songIds);

    @Query("SELECT s as song, mp as memberPart "
        + "FROM MemberPart mp "
        + "LEFT JOIN FETCH Song s ON mp.song = s "
        + "WHERE mp.member.id = :memberId")
    List<SongMemberPartCreatedAtDto> findByMemberId(
        @Param("memberId") final Long memberId
    );

    Optional<MemberPart> findByMemberAndSong(final Member member, final Song song);

    boolean existsByMemberAndSong(final Member member, final Song song);
}
