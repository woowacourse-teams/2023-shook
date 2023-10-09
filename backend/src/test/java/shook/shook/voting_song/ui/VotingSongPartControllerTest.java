package shook.shook.voting_song.ui;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;
import shook.shook.auth.application.TokenProvider;
import shook.shook.auth.ui.Authority;
import shook.shook.auth.ui.argumentresolver.MemberInfo;
import shook.shook.member.domain.Member;
import shook.shook.member.domain.repository.MemberRepository;
import shook.shook.song.domain.Artist;
import shook.shook.song.domain.ArtistName;
import shook.shook.song.domain.ProfileImageUrl;
import shook.shook.song.domain.repository.ArtistRepository;
import shook.shook.voting_song.application.VotingSongPartService;
import shook.shook.voting_song.application.dto.VotingSongPartRegisterRequest;
import shook.shook.voting_song.domain.VotingSong;
import shook.shook.voting_song.domain.repository.VotingSongRepository;

@Sql("classpath:/killingpart/initialize_killing_part_song.sql")
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class VotingSongPartControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private VotingSongRepository votingSongRepository;

    @Autowired
    private ArtistRepository artistRepository;

    @Autowired
    private VotingSongPartService votingSongPartService;

    @DisplayName("투표중인 노래에 파트를 등록 성공시 201 상태코드를 반환한다.")
    @Test
    void registerPart() {
        //given
        final VotingSong votingSong = getSavedSong();
        final String accessToken = getToken(1L, "nickname");
        final VotingSongPartRegisterRequest request = new VotingSongPartRegisterRequest(1, 10);

        //when
        //then
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all().post("/voting-songs/{songId}/parts", votingSong.getId())
            .then().statusCode(HttpStatus.CREATED.value());
    }

    @DisplayName("멤버가 똑같은 파트를 등록한 경우 200 상태코드를 반환한다.")
    @Test
    void registerPart_whenMemberSamePartExist() {
        //given
        final VotingSong votingSong = getSavedSong();
        final String accessToken = getToken(1L, "nickname");
        final Member member = getMember();
        final VotingSongPartRegisterRequest request = new VotingSongPartRegisterRequest(1, 10);
        addPartAndVote(votingSong, member, request);

        //when
        //then
        RestAssured.given().log().all()
            .contentType(ContentType.JSON)
            .body(request)
            .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
            .when().log().all().post("/voting-songs/{songId}/parts", votingSong.getId())
            .then().statusCode(HttpStatus.OK.value());
    }

    private String getToken(final Long memberId, final String nickname) {
        return tokenProvider.createAccessToken(memberId, nickname);
    }

    private VotingSong getSavedSong() {
        final Artist artist = new Artist(new ProfileImageUrl("profile"), new ArtistName("가수"));
        artistRepository.save(artist);
        return votingSongRepository.save(new VotingSong(
            "title",
            "12345678901",
            "albumCover",
            artist,
            100)
        );
    }

    private Member getMember() {
        return memberRepository.findById(1L).orElseThrow(RuntimeException::new);
    }

    private void addPartAndVote(final VotingSong song,
                                final Member member,
                                final VotingSongPartRegisterRequest request) {
        votingSongPartService.registerAndReturnMemberPartDuplication(
            new MemberInfo(member.getId(), Authority.MEMBER),
            song.getId(),
            request
        );
    }
}
