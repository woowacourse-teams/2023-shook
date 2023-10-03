package shook.shook.member.ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import shook.shook.auth.application.TokenProvider;
import shook.shook.member.application.dto.NicknameUpdateRequest;
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
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all()
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
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all()
            .delete("/members/{member_id}", member.getId())
            .then().log().all()
            .statusCode(HttpStatus.FORBIDDEN.value());
    }

    @DisplayName("닉네임 수정 시 200 상태 코드와 새로운 토큰이 반환된다.")
    @Test
    void updateNickname_OK() {
        // given
        final Member member = memberRepository.save(new Member("hi@naver.com", "hi"));
        final String accessToken = tokenProvider.createAccessToken(member.getId(), member.getNickname());

        final NicknameUpdateRequest request = new NicknameUpdateRequest("newNickname");

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .patch("/members/{member_id}/nickname", member.getId())
            .then().log().all()
            .statusCode(HttpStatus.OK.value());
    }

    @DisplayName("동일한 닉네임으로 수정 시 204 상태 코드가 반환된다.")
    @Test
    void updateNickname_noContent() {
        // given
        final Member member = memberRepository.save(new Member("hi@naver.com", "nickname"));
        final String accessToken = tokenProvider.createAccessToken(member.getId(),
                                                                   member.getNickname());

        final NicknameUpdateRequest request = new NicknameUpdateRequest("nickname");

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .patch("/members/{member_id}/nickname", member.getId())
            .then().log().all()
            .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("유효하지 않은 닉네임으로 닉네임 수정 시도 시 400 상태코드가 반환된다.")
    @ValueSource(strings = {"", " ", "  ", "hi", "닉네임이너무너무너무너무너무너무너무길어요"})
    @ParameterizedTest
    void updateNickname_badRequest(final String invalidNickname) {
        // given
        final Member member = memberRepository.save(new Member("hi@naver.com", "hi"));
        final Member newMember = memberRepository.save(new Member("new@naver.com", "new"));
        final String accessToken = tokenProvider.createAccessToken(newMember.getId(),
                                                                   newMember.getNickname());

        final NicknameUpdateRequest request = new NicknameUpdateRequest(invalidNickname);

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .contentType(ContentType.JSON)
            .body(request)
            .when().log().all()
            .patch("/members/{member_id}/nickname", newMember.getId())
            .then().log().all()
            .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
