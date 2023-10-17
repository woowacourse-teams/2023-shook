package shook.shook.my_part.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import shook.shook.my_part.domain.MemberPart;
import shook.shook.my_part.domain.repository.dto.SongMemberPartCreatedAtDto;

import java.util.List;
import java.util.Optional;

public interface MemberPartRepository extends JpaRepository<MemberPart, Long> {

    Optional<MemberPart> findByMemberIdAndId(final Long memberId, final Long memberPartId);

    @Query("SELECT s as song, mp as memberPart "
            + "FROM MemberPart mp "
            + "LEFT JOIN FETCH Song s ON mp.song = s "
            + "WHERE mp.member.id = :memberId")
    List<SongMemberPartCreatedAtDto> findByMemberId(
            @Param("memberId") final Long memberId
    );
}
