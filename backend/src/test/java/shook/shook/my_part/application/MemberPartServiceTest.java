package shook.shook.my_part.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.my_part.application.dto.MemberPartRegisterRequest;
import shook.shook.my_part.domain.repository.MemberPartRepository;
import shook.shook.song.domain.repository.SongRepository;
import shook.shook.song.exception.SongException;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class MemberPartServiceTest extends UsingJpaTest {

    private MemberPartService memberPartService;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private MemberPartRepository memberPartRepository;

    @BeforeEach
    void setUp() {
        memberPartService = new MemberPartService(songRepository, memberRepository, memberPartRepository);
    }

    @DisplayName("존재하지 않는 노래에 멤버 파트를 등록하면 예외가 발생한다")
    @Test
    void register_failNotExistSong() {
        // given
        final MemberPartRegisterRequest request = new MemberPartRegisterRequest(5, 5);
        final Long notExistSongId = 0L;

        // when
        // then
        assertThatThrownBy(() -> memberPartService.register(notExistSongId, 1L, request))
            .isInstanceOf(SongException.SongNotExistException.class);
    }

    @DisplayName("존재하지 않는 멤버로 멤버 파트를 등록하면 예외가 발생한다")
    @Test
    void register_failNotExist() {
        // given
        final MemberPartRegisterRequest request = new MemberPartRegisterRequest(5, 5);
        final Long notExistMemberId = 0L;

        // when
        // then
        assertThatThrownBy(() -> memberPartService.register(1L, notExistMemberId, request))
            .isInstanceOf(MemberException.MemberNotExistException.class);
    }
}
