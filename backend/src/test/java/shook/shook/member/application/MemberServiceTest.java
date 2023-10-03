package shook.shook.member.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.repository.InMemoryTokenPairRepository;
import shook.shook.auth.ui.Authority;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.application.dto.NicknameUpdateRequest;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.song.domain.killingpart.KillingPart;
import shook.shook.song.domain.killingpart.KillingPartComment;
import shook.shook.song.domain.killingpart.KillingPartLike;
import shook.shook.song.domain.killingpart.repository.KillingPartCommentRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartLikeRepository;
import shook.shook.song.domain.killingpart.repository.KillingPartRepository;
import shook.shook.support.UsingJpaTest;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
class MemberServiceTest extends UsingJpaTest {

    private static Member savedMember;
    private static final long ACCESS_TOKEN_VALID_TIME = 12000L;
    private static final long REFRESH_TOKEN_VALID_TIME = 6048000L;
    private static final String SECRET_CODE = "2345asdfasdfsadfsdf243dfdsfsfs";

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private KillingPartRepository killingPartRepository;

    @Autowired
    private KillingPartCommentRepository partCommentRepository;

    @Autowired
    private KillingPartLikeRepository likeRepository;

    private TokenProvider tokenProvider;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(ACCESS_TOKEN_VALID_TIME,
                                          REFRESH_TOKEN_VALID_TIME,
                                          SECRET_CODE);
        memberService = new MemberService(memberRepository, partCommentRepository, likeRepository, tokenProvider,
                                          new InMemoryTokenPairRepository());
        savedMember = memberRepository.save(new Member("woowa@wooteco.com", "shook"));
    }

    @DisplayName("회원을 등록한다.")
    @Test
    void register() {
        //given
        final String email = "shook@wooteco.com";

        //when
        final Member result = memberService.register(email);

        //then
        final Member expect = memberService.findByEmail(email).get();

        assertThat(result.getEmail()).isEqualTo(expect.getEmail());
        assertThat(result.getNickname()).isEqualTo(expect.getNickname());
    }

    @DisplayName("중복된 이메일로 회원을 등록되는 경우 예외를 던진다.")
    @Test
    void register_fail_alreadyExistMember() {
        // given
        final String email = "woowa@wooteco.com";

        // when
        // then
        assertThatThrownBy(() -> memberService.register(email))
            .isInstanceOf(MemberException.ExistMemberException.class);
    }

    @DisplayName("회원을 이메일로 조회한다.")
    @Test
    void findByEmail() {
        //given
        //when
        final Member result = memberService.findByEmail(savedMember.getEmail()).get();

        //then
        assertThat(result.getId()).isEqualTo(savedMember.getId());
        assertThat(result.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(result.getNickname())
            .isEqualTo(savedMember.getNickname());
    }

    @DisplayName("회원을 id와 nickname으로 조회한다.")
    @Test
    void success_findByIdAndNickname() {
        //given
        //when
        final Member result = memberService.findByIdAndNicknameThrowIfNotExist(
            savedMember.getId(),
            new Nickname(savedMember.getNickname()));

        //then
        assertThat(result).usingRecursiveComparison()
            .isEqualTo(savedMember);
    }

    @DisplayName("회원을 id와 nickname으로 조회할 때 회원의 닉네임이 잘못되면 예외를 던진다.")
    @Test
    void fail_findByIdAndNickname_wrong_nickname() {
        //given
        //when
        //then
        assertThatThrownBy(
            () -> memberService.findByIdAndNicknameThrowIfNotExist(savedMember.getId(),
                                                                   new Nickname(savedMember.getNickname() + "none")))
            .isInstanceOf(MemberException.MemberNotExistException.class);
    }

    @DisplayName("회원을 id와 nickname으로 조회할 때 회원의 id가 잘못되면 예외를 던진다.")
    @Test
    void fail_findByIdAndNickname_wrong_memberId() {
        //given
        //when
        //then
        assertThatThrownBy(
            () -> memberService.findByIdAndNicknameThrowIfNotExist(savedMember.getId() + 1,
                                                                   new Nickname(savedMember.getNickname())))
            .isInstanceOf(MemberException.MemberNotExistException.class);
    }

    @DisplayName("회원을 id와 nickname으로 조회할 때 회원의 id와 nickname 모두 잘못되면 예외를 던진다.")
    @Test
    void fail_findByIdAndNickname_wrong_memberId_and_nickname() {
        //given
        //when
        //then
        assertThatThrownBy(
            () -> memberService.findByIdAndNicknameThrowIfNotExist(savedMember.getId() + 1,
                                                                   new Nickname(savedMember.getNickname() + "none")))
            .isInstanceOf(MemberException.MemberNotExistException.class);
    }

    @DisplayName("회원 id로 회원을 삭제시 회원의 댓글을 모두 삭제하고 회원의 좋아요는 삭제 상태로 변환한다.")
    @Test
    void success_delete() {
        // given
        final Long targetId = savedMember.getId();

        final KillingPart killingPart = killingPartRepository.findById(1L).get();
        likeRepository.save(new KillingPartLike(killingPart, savedMember));
        partCommentRepository.save(KillingPartComment.forSave(killingPart, "hi", savedMember));

        saveAndClearEntityManager();
        // when
        memberService.deleteById(targetId, new MemberInfo(targetId, Authority.MEMBER));

        // then
        assertThat(likeRepository.findAllByMemberAndIsDeleted(savedMember, false)).isEmpty();
        assertThat(partCommentRepository.findAllByMember(savedMember)).isEmpty();
        assertThat(memberRepository.findById(targetId)).isEmpty();
    }

    @DisplayName("회원 id로 회원을 삭제할 때, 존재하지 않는 id 라면 예외가 발생한다.")
    @Test
    void fail_delete() {
        // given
        final long unsavedMemberId = Long.MAX_VALUE;
        final Long targetId = unsavedMemberId;

        // when, then
        assertThatThrownBy(() ->
                               memberService.deleteById(targetId, new MemberInfo(unsavedMemberId, Authority.MEMBER))
        ).isInstanceOf(MemberException.MemberNotExistException.class);
    }

    @DisplayName("회원 id로 회원을 삭제할 때, token 에 담긴 회원과 대상 회원이 다르다면 예외가 발생한다.")
    @Test
    void fail_delete_unauthenticated() {
        // given
        final Member targetMember = savedMember;
        final Member requestMember = memberRepository.save(new Member("hi@email.com", "hi"));

        // when, then
        assertThatThrownBy(() ->
                               memberService.deleteById(targetMember.getId(),
                                                        new MemberInfo(requestMember.getId(), Authority.MEMBER))
        ).isInstanceOf(AuthorizationException.UnauthenticatedException.class);
    }

    @DisplayName("닉네임을 변경한다.")
    @Nested
    class NicknameUpdate {

        @DisplayName("변경할 닉네임으로 닉네임을 변경한다.")
        @ValueSource(strings = {"newNickname", "newNickname123", "newNickname1234", "한글도20자를닉네임으로사용할수있습니다"})
        @ParameterizedTest
        void success_update(final String newNickname) {
            // given
            final NicknameUpdateRequest request = new NicknameUpdateRequest(newNickname);

            // when
            memberService.updateNickname(savedMember.getId(), savedMember.getId(), request);

            // then
            assertThat(memberRepository.findById(savedMember.getId()).get().getNickname())
                .isEqualTo(newNickname);
        }

        @DisplayName("기존 닉네임과 동일한 닉네임으로 변경하는 경우, false 를 리턴한다.")
        @Test
        void success_updateNickname_same_nickname_before() {
            // given
            final NicknameUpdateRequest request = new NicknameUpdateRequest(savedMember.getNickname());

            // when
            // then
            assertThat(memberService.updateNickname(savedMember.getId(), savedMember.getId(), request)).isFalse();
        }

        @DisplayName("변경할 닉네임이 중복되면 예외를 던진다.")
        @Test
        void fail_updateNickname_duplicate_nickname() {
            // given
            final Member newMember = memberRepository.save(new Member("temp@email", "shook2"));
            final String duplicateNickname = "shook";
            final NicknameUpdateRequest request = new NicknameUpdateRequest(duplicateNickname);

            // when
            // then
            assertThatThrownBy(() -> memberService.updateNickname(newMember.getId(), newMember.getId(), request))
                .isInstanceOf(MemberException.ExistNicknameException.class);
        }

        @DisplayName("닉네임이 빈 문자열이면 예외를 던진다.")
        @ValueSource(strings = {"", " ", "  ", "\r", "\n", "\t"})
        @ParameterizedTest
        void fail_updateNickname_empty_nickname(final String emptyValue) {
            // given
            final NicknameUpdateRequest request = new NicknameUpdateRequest(emptyValue);

            // when
            // then
            assertThatThrownBy(() -> memberService.updateNickname(savedMember.getId(), savedMember.getId(), request))
                .isInstanceOf(MemberException.NullOrEmptyNicknameException.class);
        }

        @DisplayName("변경할 닉네임 길이가 20자를 초과하면 예외를 던진다.")
        @ValueSource(strings = {"veryverylonglonglongnickname", "123456789012345678901", "입력한닉네임이너무길어서업데이트할수없어요"})
        @ParameterizedTest
        void fail_updateNickname_too_long_nickname(final String tooLongNickname) {
            // given
            final NicknameUpdateRequest request = new NicknameUpdateRequest(tooLongNickname);

            // when
            // then
            assertThatThrownBy(() -> memberService.updateNickname(savedMember.getId(), savedMember.getId(), request))
                .isInstanceOf(MemberException.TooLongNicknameException.class);
        }
    }
}
