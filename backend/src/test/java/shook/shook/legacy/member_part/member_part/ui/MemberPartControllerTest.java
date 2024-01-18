package shook.shook.legacy.member_part.member_part.ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.legacy.auth.application.TokenProvider;
import shook.shook.legacy.member.domain.Member;
import shook.shook.legacy.member.domain.repository.MemberRepository;
import shook.shook.legacy.member_part.application.dto.MemberPartRegisterRequest;
import shook.shook.legacy.member_part.domain.MemberPart;
import shook.shook.legacy.member_part.domain.repository.MemberPartRepository;
import shook.shook.legacy.song.domain.Song;
import shook.shook.legacy.song.domain.repository.SongRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class MemberPartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private MemberPartRepository memberPartRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버의 파트를 등록 성공 시 201 상태 코드를 반환한다.")
    @Test
    void registerMemberPart() {
        // given
        final Song song = songRepository.findById(1L).get();
        final Member member = memberRepository.findById(1L).get();
        final MemberPartRegisterRequest request = new MemberPartRegisterRequest(5, 10);
        final String accessToken = tokenProvider.createAccessToken(member.getId(), member.getNickname());

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .body(request)
            .contentType(ContentType.JSON)
            .when().log().all().post("/songs/{songId}/member-parts", song.getId())
            .then().statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("잘못된 정보로 파트를 등록 시 400 상태 코드를 반환한다.")
    @CsvSource({"-1, 10", "190, 15", "5, 25"})
    @ParameterizedTest
    void register_failBadRequest(final int startSecond, final int length) {
        // given
        final Song song = songRepository.findById(1L).get();
        final Member member = memberRepository.findById(1L).get();
        final MemberPartRegisterRequest request = new MemberPartRegisterRequest(startSecond, length);
        final String accessToken = tokenProvider.createAccessToken(member.getId(), member.getNickname());

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .body(request)
            .contentType(ContentType.JSON)
            .when().log().all().post("/songs/{songId}/member-parts", song.getId())
            .then().statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @DisplayName("멤버 파트 삭제 성공 시 204 상태 코드를 반환한다.")
    @Test
    void delete() {
        // given
        final Song song = songRepository.findById(1L).get();
        final Member member = memberRepository.findById(1L).get();
        final String accessToken = tokenProvider.createAccessToken(member.getId(), member.getNickname());
        final MemberPart memberPart = memberPartRepository.save(MemberPart.forSave(5, 10, song, member));

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all().delete("/member-parts/{memberPartId}", memberPart.getId())
            .then().statusCode(HttpStatus.NO_CONTENT.value());
    }

    @DisplayName("자신의 파트가 아닌 파트를 삭제하려고 시도할 시 403 상태 코드를 반환한다.")
    @Test
    void delete_failUnauthorizedMember() {
        // given
        final Song song = songRepository.findById(1L).get();
        final Member member = memberRepository.findById(1L).get();
        final MemberPart memberPart = memberPartRepository.save(MemberPart.forSave(5, 10, song, member));
        final Member otherMember = memberRepository.save(new Member("other@email.com", "nickname"));
        final String otherMemberAccessToken = tokenProvider.createAccessToken(otherMember.getId(),
                                                                              otherMember.getNickname());

        // when
        // then
        RestAssured.given().log().all()
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + otherMemberAccessToken)
            .when().log().all().delete("/member-parts/{memberPartId}", memberPart.getId())
            .then().statusCode(HttpStatus.FORBIDDEN.value());
    }
}
