package shook.shook.exceptionhandler;

import static org.mockito.BDDMockito.given;

import io.restassured.RestAssured;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.mock.mockito.MockBean;
import shook.shook.auth.jwt.exception.TokenException;
import shook.shook.auth.oauth.exception.OAuthException;
import shook.shook.globalexception.CustomException;
import shook.shook.part.exception.PartException;
import shook.shook.song.application.SongService;
import shook.shook.song.application.killingpart.dto.HighLikedSongResponse;
import shook.shook.song.exception.SongException;
import shook.shook.song.exception.killingpart.KillingPartCommentException;
import shook.shook.song.exception.killingpart.KillingPartException;
import shook.shook.song.exception.killingpart.KillingPartLikeException;
import shook.shook.song.exception.killingpart.KillingPartsException;
import shook.shook.support.AcceptanceTest;
import shook.shook.voting_song.exception.VoteException;
import shook.shook.voting_song.exception.VotingSongException;
import shook.shook.voting_song.exception.VotingSongPartException;

public class ControllerAdviceTest extends AcceptanceTest {

    @MockBean
    private SongService mockedService;

    @DisplayName("Controller Advice를 테스트한다.")
    @ParameterizedTest
    @MethodSource("exceptionTestData")
    public void testGlobalExceptionHandler(ExceptionTestData testData) {
        final CustomException exception = testData.getException();
        mockedService.showHighLikedSongs();
        final List<HighLikedSongResponse> responses = mockedService.showHighLikedSongs();
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

            new ExceptionTestData(new OAuthException.InvalidEmailException(), 503),
            new ExceptionTestData(new OAuthException.InvalidAuthorizationCodeException(), 503),
            new ExceptionTestData(new OAuthException.InvalidAccessTokenException(), 503),
            new ExceptionTestData(new OAuthException.GoogleServerException(), 503),

            new ExceptionTestData(new KillingPartCommentException.NullOrEmptyPartCommentException(),
                400),
            new ExceptionTestData(new KillingPartCommentException.TooLongPartCommentException(),
                400),
            new ExceptionTestData(new KillingPartCommentException.DuplicateCommentExistException(),
                500),
            new ExceptionTestData(new KillingPartCommentException.CommentForOtherPartException(),
                500),

            new ExceptionTestData(new KillingPartException.PartNotExistException(), 400),
            new ExceptionTestData(new KillingPartException.SongNotUpdatableException(), 400),
            new ExceptionTestData(new KillingPartException.SongMaxKillingPartExceededException(),
                400),

            new ExceptionTestData(new KillingPartLikeException.LikeForOtherKillingPartException(),
                400),
            new ExceptionTestData(new KillingPartLikeException.EmptyLikeException(), 500),

            new ExceptionTestData(new KillingPartsException.OutOfSizeException(), 400),
            new ExceptionTestData(new KillingPartsException.EmptyKillingPartsException(), 400),

            new ExceptionTestData(new SongException.SongLengthLessThanOneException(), 400),
            new ExceptionTestData(new SongException.SongNotExistException(), 400),
            new ExceptionTestData(new SongException.NullOrEmptyTitleException(), 400),
            new ExceptionTestData(new SongException.TooLongTitleException(), 400),
            new ExceptionTestData(new SongException.NullOrEmptyVideoUrlException(), 400),
            new ExceptionTestData(new SongException.TooLongTitleException(), 400),
            new ExceptionTestData(new SongException.NullOrEmptyImageUrlException(), 400),
            new ExceptionTestData(new SongException.TooLongImageUrlException(), 400),
            new ExceptionTestData(new SongException.NullOrEmptySingerNameException(), 400),
            new ExceptionTestData(new SongException.TooLongSingerNameException(), 400),

            new ExceptionTestData(new PartException.StartLessThanZeroException(), 400),
            new ExceptionTestData(new PartException.StartOverSongLengthException(), 400),
            new ExceptionTestData(new PartException.EndOverSongLengthException(), 400),
            new ExceptionTestData(new PartException.InvalidLengthException(), 400),
            new ExceptionTestData(new PartException.DuplicateStartAndLengthException(), 500),

            new ExceptionTestData(new VotingSongPartException.PartNotExistException(), 400),
            new ExceptionTestData(new VotingSongPartException.PartForOtherSongException(), 500),
            new ExceptionTestData(new VotingSongException.VotingSongNotExistException(), 400),
            new ExceptionTestData(new VoteException.VoteForOtherPartException(), 500),
            new ExceptionTestData(new VoteException.DuplicateVoteExistException(), 500)
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
