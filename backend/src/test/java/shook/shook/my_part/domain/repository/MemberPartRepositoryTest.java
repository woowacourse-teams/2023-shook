package shook.shook.my_part.domain.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.my_part.domain.MemberPart;
import shook.shook.my_part.domain.repository.dto.SongMemberPartCreatedAtDto;
import shook.shook.song.domain.Song;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.support.UsingJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Sql(scripts = "classpath:killingpart/initialize_killing_part_song.sql")
class MemberPartRepositoryTest extends UsingJpaTest {

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPartRepository memberPartRepository;

    @DisplayName("나의 파트를 조회한다.")
    @Test
    void find_myPart() {
        // given
        final Song savedSong = songRepository.findById(1L).get();

        final Member member = new Member("shook@s.com", "shook1");
        final Member savedMember = memberRepository.save(member);


        final MemberPart memberPart = MemberPart.forSave(10, 15, savedSong, savedMember);
        final MemberPart savedMemberPart = memberPartRepository.save(memberPart);

        // when
        final List<SongMemberPartCreatedAtDto> result = memberPartRepository.findByMemberId(savedMember.getId());

        // then
        assertThat(result.size()).isOne();
        assertThat(result.get(0).getSong()).isEqualTo(savedSong);
        assertThat(result.get(0).getMemberPart()).isEqualTo(savedMemberPart);
    }
}
