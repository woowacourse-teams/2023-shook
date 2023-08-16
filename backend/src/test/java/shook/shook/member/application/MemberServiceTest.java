package shook.shook.member.application;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import shook.shook.member.domain.Email;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.Nickname;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.member.exception.MemberException;
import shook.shook.member.exception.MemberException.MemberNotExistException;
import shook.shook.support.UsingJpaTest;

class MemberServiceTest extends UsingJpaTest {

    private static Member savedMember;

    @Autowired
    private MemberRepository memberRepository;

    private MemberService memberService;

    @BeforeEach
    void setUp() {
        memberService = new MemberService(memberRepository);
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
        final String nickname = email.split("@")[0];

        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getNickname()).isEqualTo(nickname);
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
        final Member result = memberService.findByEmail(
            new Email(savedMember.getEmail())).get();

        //then
        assertThat(result.getId()).isEqualTo(savedMember.getId());
        assertThat(result.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(result.getNickname()).isEqualTo(savedMember.getNickname());
    }

    @DisplayName("회원을 id로 조회한다.")
    @Test
    void success_findById() {
        //given
        //when
        final Member result = memberService.findById(savedMember.getId());

        //then
        assertThat(result.getId()).isEqualTo(savedMember.getId());
        assertThat(result.getEmail()).isEqualTo(savedMember.getEmail());
        assertThat(result.getNickname()).isEqualTo(savedMember.getNickname());
    }

    @DisplayName("회원을 id로 조회할 때 존재하지 않으면 예외를 던진다.")
    @Test
    void fail_findById() {
        //given
        //when
        //then
        assertThatThrownBy(() -> memberService.findById(Long.MAX_VALUE))
            .isInstanceOf(MemberNotExistException.class);
    }

    @DisplayName("회원을 id와 nickname으로 조회한다.")
    @Test
    void success_findByIdAndNickname() {
        //given
        //when
        final Member result = memberService.findByIdAndNickname(
            savedMember.getId(),
            new Nickname(savedMember.getNickname()));

        //then
        assertThat(result).usingRecursiveComparison().isEqualTo(savedMember);
    }

    @DisplayName("회원을 id와 nickname으로 조회할 때 회원의 닉네임이 잘못되면 예외를 던진다.")
    @Test
    void fail_findByIdAndNickname_wrong_nickname() {
        //given
        //when
        //then
        assertThatThrownBy(
            () -> memberService.findByIdAndNickname(savedMember.getId(),
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
            () -> memberService.findByIdAndNickname(savedMember.getId() + 1,
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
            () -> memberService.findByIdAndNickname(savedMember.getId() + 1,
                new Nickname(savedMember.getNickname() + "none")))
            .isInstanceOf(MemberException.MemberNotExistException.class);
    }
}
