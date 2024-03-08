package shook.shook.improved.exceptionhandler;

import static org.mockito.BDDMockito.given;

import io.restassured.RestAssured;
import java.util.stream.Stream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import shook.shook.improved.artist.exception.ArtistException;
import shook.shook.improved.auth.exception.AuthorizationException;
import shook.shook.improved.auth.exception.OAuthException;
import shook.shook.improved.auth.exception.TokenException;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.member.exception.MemberException;
import shook.shook.improved.member.exception.MemberException.TooLongIdentifierException;
import shook.shook.improved.part.exception.PartException;
import shook.shook.improved.song.exception.SongException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartCommentException.CommentForOtherPartException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartCommentException.DuplicateCommentExistException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartCommentException.NullOrEmptyPartCommentException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartCommentException.TooLongPartCommentException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartLikeException;
import shook.shook.improved.song.exception.legacy_killingpart.KillingPartsException;
import shook.shook.improved.support.AcceptanceTest;
import shook.shook.legacy.song.application.SongService;
import shook.shook.legacy.voting_song.exception.VoteException;
import shook.shook.legacy.voting_song.exception.VotingSongException;
import shook.shook.legacy.voting_song.exception.VotingSongPartException;

@Disabled
class ControllerAdviceTest extends AcceptanceTest {

    @MockBean
    private SongService mockedService;

    @DisplayName("Controller Advice를 테스트한다.")
    @ParameterizedTest
    @MethodSource("exceptionTestData")
    void testGlobalExceptionHandler(ExceptionTestData testData) {
        given(mockedService.showHighLikedSongs()).willThrow(testData.getException());

        RestAssured.given().log().all()
            .when().log().all()
            .get("/songs/high-liked")
            .then().log().all()
            .statusCode(testData.statusCode);
    }

    private static Stream<ExceptionTestData> exceptionTestData() {
        return Stream.of(
            new ExceptionTestData(new TokenException.NotIssuedTokenException(), 401),
            new ExceptionTestData(new TokenException.ExpiredTokenException(), 401),
            new ExceptionTestData(new TokenException.TokenPairNotMatchingException(), 401),
            new ExceptionTestData(new TokenException.RefreshTokenNotFoundException(), 401),

            new ExceptionTestData(new OAuthException.InvalidAuthorizationCodeException(), 503),
            new ExceptionTestData(new OAuthException.InvalidAccessTokenException(), 503),
            new ExceptionTestData(new OAuthException.GoogleServerException(), 503),
            new ExceptionTestData(new AuthorizationException.AccessTokenNotFoundException(), 401),
            new ExceptionTestData(new AuthorizationException.RefreshTokenNotFoundException(), 401),
            new ExceptionTestData(new AuthorizationException.UnauthenticatedException(), 403),
            new ExceptionTestData(new MemberException.MemberNotExistException(), 401),

            new ExceptionTestData(new NullOrEmptyPartCommentException(),
                                  400),
            new ExceptionTestData(new TooLongPartCommentException(),
                                  400),
            new ExceptionTestData(new DuplicateCommentExistException(),
                                  500),
            new ExceptionTestData(new CommentForOtherPartException(),
                                  500),

            new ExceptionTestData(new KillingPartException.PartNotExistException(), 400),
            new ExceptionTestData(new KillingPartException.SongNotUpdatableException(), 500),
            new ExceptionTestData(new KillingPartException.SongMaxKillingPartExceededException(),
                                  500),

            new ExceptionTestData(new KillingPartLikeException.LikeForOtherKillingPartException(),
                                  500),
            new ExceptionTestData(new KillingPartLikeException.EmptyLikeException(), 500),

            new ExceptionTestData(new KillingPartsException.OutOfSizeException(), 500),
            new ExceptionTestData(new KillingPartsException.EmptyKillingPartsException(), 500),

            new ExceptionTestData(
                new SongException.SongLengthLessThanOneException(), 400),
            new ExceptionTestData(
                new SongException.SongNotExistException(),
                400),
            new ExceptionTestData(new SongException.NullOrEmptyTitleException(),
                                  400),
            new ExceptionTestData(new SongException.TooLongTitleException(), 400),
            new ExceptionTestData(new SongException.NullOrEmptyVideoIdException(),
                                  400),
            new ExceptionTestData(
                new SongException.IncorrectVideoIdLengthException(), 400),
            new ExceptionTestData(new SongException.NullOrEmptyImageUrlException(),
                                  400),
            new ExceptionTestData(
                new SongException.TooLongImageUrlException(), 400),
            new ExceptionTestData(
                new ArtistException.NullOrEmptyNameException(), 400),
            new ExceptionTestData(
                new ArtistException.TooLongNameException(), 400),
            new ExceptionTestData(
                new ArtistException.NullOrEmptyProfileUrlException(), 400),
            new ExceptionTestData(
                new ArtistException.TooLongProfileUrlException(), 400),

            new ExceptionTestData(new PartException.StartLessThanZeroException(), 400),
            new ExceptionTestData(new PartException.StartOverSongLengthException(), 400),
            new ExceptionTestData(new PartException.EndOverSongLengthException(), 400),
            new ExceptionTestData(new PartException.InvalidLengthException(), 400),
            new ExceptionTestData(new PartException.DuplicateStartAndLengthException(), 500),

            new ExceptionTestData(new VotingSongPartException.PartNotExistException(), 400),
            new ExceptionTestData(new VotingSongPartException.PartForOtherSongException(), 500),
            new ExceptionTestData(new VotingSongException.VotingSongNotExistException(), 400),
            new ExceptionTestData(new VoteException.VoteForOtherPartException(), 500),
            new ExceptionTestData(new VoteException.DuplicateVoteExistException(),
                                  500),

            new ExceptionTestData(new MemberException.ExistMemberException(), 400),
            new ExceptionTestData(new TooLongIdentifierException(), 400),
            new ExceptionTestData(new MemberException.InValidEmailFormException(), 400),
            new ExceptionTestData(new MemberException.NullOrEmptyEmailException(), 400),
            new ExceptionTestData(new MemberException.TooLongNicknameException(), 400),
            new ExceptionTestData(new MemberException.ExistMemberException(), 400)
        );
    }

    private static class ExceptionTestData {

        private final CustomException exception;
        private final int code;
        private final String message;
        private final int statusCode;

        public ExceptionTestData(CustomException exception, int statusCode) {
            this.exception = exception;
            this.code = exception.getCode();
            this.message = exception.getMessage();
            this.statusCode = statusCode;
        }

        public CustomException getException() {
            return exception;
        }

        public int getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public int getStatusCode() {
            return statusCode;
        }
    }
}