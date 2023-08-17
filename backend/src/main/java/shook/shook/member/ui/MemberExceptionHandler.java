package shook.shook.member.ui;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shook.shook.globalexception.ErrorResponse;
import shook.shook.member.exception.MemberException;

@Slf4j
@RestControllerAdvice
public class MemberExceptionHandler {

    @ExceptionHandler(MemberException.class)
    public ResponseEntity<ErrorResponse> handleMemberException(final MemberException e) {
        log.error(e.toString());

        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }
}
