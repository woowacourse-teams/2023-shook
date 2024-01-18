package shook.shook.legacy.member_part.member_part.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.legacy.auth.exception.AuthorizationException;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.member.exception.MemberException;
import shook.shook.legacy.member_part.application.MemberPartService;
import shook.shook.legacy.member_part.application.dto.MemberPartRegisterRequest;
import shook.shook.legacy.member_part.domain.MemberPart;
import shook.shook.legacy.member_part.domain.repository.MemberPartRepository;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.repository.SongRepository;
import shook.shook.legacy.song.exception.SongException;
import shook.shook.legacy.support.UsingJpaTest;

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

    @DisplayName("존재하지 않는 노래에 멤버 파트를 등록하면 예외가 발생한다.")
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

    @DisplayName("존재하지 않는 멤버로 멤버 파트를 등록하면 예외가 발생한다.")
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

    @DisplayName("해당 노래에 이미 멤버 파트가 존재하면 예외가 발생한다.")
    @Test
    void register_failAlreadyExistMemberPart() {
        // given
        final long songId = 1L;
        final long memberId = 1L;
        memberPartService.register(songId, memberId, new MemberPartRegisterRequest(5, 5));

        // when
        // then
        assertThatThrownBy(() -> memberPartService.register(songId, memberId, new MemberPartRegisterRequest(10, 10)))
            .isInstanceOf(MemberException.MemberPartAlreadyExistException.class);
    }

    @DisplayName("존재하지 않는 멤버로 멤버 파트를 삭제하면 예외가 발생한다.")
    @Test
    void delete_failUnauthenticatedMember() {
        // given
        final Song song = songRepository.findById(1L).get();
        final Member member = memberRepository.findById(1L).get();
        final MemberPart memberPart = memberPartRepository.save(MemberPart.forSave(20, 10, song, member));
        final Long notExistMemberId = 0L;

        // when
        // then
        assertThatThrownBy(() -> memberPartService.delete(notExistMemberId, memberPart.getId()))
            .isInstanceOf(AuthorizationException.UnauthenticatedException.class);
    }
}
