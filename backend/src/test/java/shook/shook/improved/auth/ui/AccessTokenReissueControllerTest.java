package shook.shook.improved.auth.ui;

import org.junit.jupiter.api.Disabled;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@Disabled
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class AccessTokenReissueControllerTest {

    // TODO: 3/8/24 legacy MemberRepository 의존으로 인한 주석 처리

//    @Autowired
//    private DataCleaner dataCleaner;
//
//    @LocalServerPort
//    private int port;
//
//    private static Member savedMember;
//
//    @Autowired
//    private MemberRepository memberRepository;
//
//    @Autowired
//    private TokenProvider tokenProvider;
//
//    @Autowired
//    private InMemoryTokenPairRepository inMemoryTokenPairRepository;
//
//    private String refreshToken;
//    private String accessToken;
//
//    @BeforeEach
//    void setUp() {
//        RestAssured.port = port;
//        dataCleaner.clear();
//        savedMember = memberRepository.save(new Member("shook@wooteco.com", "shook"));
//        refreshToken = tokenProvider.createRefreshToken(savedMember.getId(), savedMember.getNickname());
//        accessToken = tokenProvider.createAccessToken(savedMember.getId(), savedMember.getNickname());
//        inMemoryTokenPairRepository.addOrUpdateTokenPair(refreshToken, accessToken);
//    }
//
//    @AfterEach
//    void delete() {
//        memberRepository.delete(savedMember);
//    }
//
//    @DisplayName("올바른 refreshToken을 통해 accessToken 재발급을 요청하면 accessToken과 상태코드 200을 반환한다.")
//    @Test
//    void success_reissue_accessToken() {
//        //given
//        final String authorization = "Bearer " + accessToken;
//
//        //when
//        final ReissueAccessTokenResponse response = RestAssured.given().log().all()
//            .header("Authorization", authorization)
//            .cookie("refreshToken", refreshToken)
//            .when().log().all().post("/reissue")
//            .then().statusCode(HttpStatus.OK.value())
//            .extract().body().as(ReissueAccessTokenResponse.class);
//
//        //then
//        final Claims claims = tokenProvider.parseClaims(response.getAccessToken());
//        assertThat(claims.get("memberId", Long.class)).isEqualTo(savedMember.getId());
//        assertThat(claims.get("nickname", String.class)).isEqualTo(savedMember.getNickname());
//    }
//
//    @DisplayName("refreshToken이 없이 accessToken을 재발급 받으려면 예외를 던잔디.")
//    @Test
//    void fail_reissue_accessToken() {
//        //given
//        //when
//        //then
//        RestAssured.given().log().all()
//            .header("Authorization", "authorization")
//            .when().log().all().post("/reissue")
//            .then().statusCode(HttpStatus.UNAUTHORIZED.value());
//    }
}
