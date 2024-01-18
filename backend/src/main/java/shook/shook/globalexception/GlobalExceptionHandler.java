package shook.shook.globalexception;

import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shook.shook.auth.exception.AuthorizationException;
import shook.shook.auth.exception.OAuthException;
import shook.shook.auth.exception.TokenException;
import shook.shook.legacy.member.exception.MemberException;
import shook.shook.legacy.member_part.exception.MemberPartException;
import shook.shook.legacy.part.exception.PartException;
import shook.shook.legacy.song.exception.ArtistException;
import shook.shook.legacy.song.exception.SongException;
import shook.shook.legacy.song.exception.killingpart.KillingPartCommentException;
import shook.shook.legacy.song.exception.killingpart.KillingPartException;
import shook.shook.legacy.song.exception.killingpart.KillingPartLikeException;
import shook.shook.legacy.song.exception.killingpart.KillingPartsException;
import shook.shook.legacy.voting_song.exception.VoteException;
import shook.shook.legacy.voting_song.exception.VotingSongException;
import shook.shook.legacy.voting_song.exception.VotingSongPartException;

// TODO 2024/01/18 : migration 완료 후 재작성
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final int METHOD_ARGUMENT_FIRST_ERROR_INDEX = 0;

    @ExceptionHandler(OAuthException.class)
    public ResponseEntity<ErrorResponse> handleExternalLoginException(final OAuthException e) {
        log.error(e.getErrorInfoLog());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
        TokenException.class,
        AuthorizationException.class,
        MemberException.MemberNotExistException.class
    })
    public ResponseEntity<ErrorResponse> handleTokenException(final CustomException e) {
        log.error(e.getErrorInfoLog());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
        PartException.InvalidLengthException.class,
        SongException.class,
        KillingPartException.PartNotExistException.class,
        KillingPartCommentException.class,
        MemberException.class,
        VotingSongException.class,
        VotingSongPartException.PartNotExistException.class,
        PartException.class,
        ArtistException.class,
        MemberPartException.class
    })
    public ResponseEntity<ErrorResponse> handleGlobalBadRequestException(final CustomException e) {
        log.error(e.getErrorInfoLog());

        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(AuthorizationException.UnauthenticatedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthenticatedException(final CustomException e) {
        log.error(e.getErrorInfoLog());

        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleRequestValidationException(
        final MethodArgumentNotValidException e
    ) {
        final FieldError firstErrorField = e.getFieldErrors()
            .get(METHOD_ARGUMENT_FIRST_ERROR_INDEX);
        final CustomException exception = CustomException.fromFieldError(firstErrorField);

        log.error(exception.getErrorInfoLog());

        return ResponseEntity.badRequest().body(ErrorResponse.from(exception));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleEmptyPathVariableException(
        final MissingPathVariableException e
    ) {
        final CustomException exception = CustomException.of(
            ErrorCode.WRONG_REQUEST_URL,
            Map.of(e.getVariableName(), "")
        );

        log.error(exception.getErrorInfoLog());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(exception));
    }

    @ExceptionHandler({
        KillingPartException.class,
        KillingPartLikeException.EmptyLikeException.class,
        KillingPartCommentException.CommentForOtherPartException.class,
        KillingPartCommentException.DuplicateCommentExistException.class,
        KillingPartsException.class,
        KillingPartLikeException.class,
        VotingSongPartException.PartForOtherSongException.class,
        PartException.DuplicateStartAndLengthException.class,
        VoteException.class
    })
    public ResponseEntity<ErrorResponse> handleInternalKillingPartException(
        final CustomException e) {
        log.error(e.getErrorInfoLog());

        return ResponseEntity.internalServerError().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleInternalServer(final Exception e) {
        final CustomException customException = CustomException.from(
            ErrorCode.INTERNAL_SERVER_ERROR);

        log.error(e.toString());

        return ResponseEntity.internalServerError().body(ErrorResponse.from(customException));
    }
}
