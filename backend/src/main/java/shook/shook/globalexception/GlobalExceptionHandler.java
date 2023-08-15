package shook.shook.globalexception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shook.shook.auth.jwt.exception.TokenException;
import shook.shook.auth.oauth.exception.OAuthException;
import shook.shook.part.exception.PartException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    public static final int METHOD_ARGUMENT_FIRST_ERROR_INDEX = 0;

    @ExceptionHandler(OAuthException.class)
    public ResponseEntity<ErrorResponse> handleExternalLoginException(final OAuthException e) {
        log.error(e.toString());

        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(TokenException.class)
    public ResponseEntity<ErrorResponse> handleTokenException(final TokenException e) {
        log.error(e.toString());

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse.from(e));
    }

    @ExceptionHandler(PartException.InvalidLengthException.class)
    public ResponseEntity<ErrorResponse> handleGlobalBadRequestException(final CustomException e) {
        log.error(e.toString());

        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleRequestValidationException(
        final MethodArgumentNotValidException e
    ) {
        final FieldError firstErrorField = e.getFieldErrors()
            .get(METHOD_ARGUMENT_FIRST_ERROR_INDEX);
        final CustomException exception = new CustomException(
            ErrorCode.REQUEST_BODY_VALIDATION_FAIL);

        log.error(firstErrorField.getDefaultMessage());

        return ResponseEntity.badRequest()
            .body(ErrorResponse.fromMethodArgumentException(exception, firstErrorField));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ErrorResponse> handleEmptyPathVariableException(
        final MissingPathVariableException e
    ) {
        final CustomException exception = new CustomException(ErrorCode.WRONG_REQUEST_URL);
        log.error(exception.toString());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorResponse.from(exception));
    }
}
