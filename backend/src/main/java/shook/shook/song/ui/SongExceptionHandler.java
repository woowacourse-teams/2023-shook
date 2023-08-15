package shook.shook.song.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorResponse;
import shook.shook.song.exception.SongException;
import shook.shook.song.exception.killingpart.KillingPartCommentException;
import shook.shook.song.exception.killingpart.KillingPartException;
import shook.shook.song.exception.killingpart.KillingPartLikeException;
import shook.shook.song.exception.killingpart.KillingPartsException;

@RestControllerAdvice
public class SongExceptionHandler {

    @ExceptionHandler(SongException.class)
    public ResponseEntity<ErrorResponse> handleSongException(final CustomException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
        KillingPartLikeException.EmptyLikeException.class,
        KillingPartCommentException.CommentForOtherPartException.class,
        KillingPartCommentException.DuplicateCommentExistException.class
    })
    public ResponseEntity<ErrorResponse> handleInternalKillingPartException(
        final CustomException e) {
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
        KillingPartException.class,
        KillingPartCommentException.class,
        KillingPartsException.class,
        KillingPartLikeException.LikeForOtherKillingPartException.class
    })
    public ResponseEntity<ErrorResponse> handleKillingPartException(final CustomException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }
}
