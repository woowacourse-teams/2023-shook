package shook.shook.member.ui;

import io.restassured.RestAssured;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import shook.shook.auth.application.TokenProvider;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.support.AcceptanceTest;

class MemberControllerTest extends AcceptanceTest {

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private TokenProvider tokenProvider;

    @DisplayName("회원 삭제시 204 상태코드와 함께 비어있는 body 응답이 반환된다.")
    @Test
    void deleteMember() {
        // given
        final Member member = memberRepository.save(new Member("hi@naver.com", "hi"));
        final String accessToken = tokenProvider.createAccessToken(member.getId(),
            member.getNickname());

        // when, then
        RestAssured.given().log().all()
            .when().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .delete("/members/{member_id}", member.getId())
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("회원 삭제시 api path에 담긴 회원 id와 토큰에 담긴 회원 id가 다르다면 403 상태코드가 반환된다.")
    @Test
    void deleteMember_forbidden() {
        // given
        final Member member = memberRepository.save(new Member("hi@naver.com", "hi"));
        final Member requestMember = memberRepository.save(new Member("new@naver.com", "new"));
        final String accessToken = tokenProvider.createAccessToken(requestMember.getId(),
            requestMember.getNickname());

        // when, then
        RestAssured.given().log().all()
            .when().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .delete("/members/{member_id}", member.getId())
            .then().log().all()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }
}
