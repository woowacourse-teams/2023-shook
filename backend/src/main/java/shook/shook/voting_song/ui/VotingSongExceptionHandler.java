package shook.shook.voting_song.ui;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorResponse;
import shook.shook.part.exception.PartException;
import shook.shook.voting_song.exception.VoteException;
import shook.shook.voting_song.exception.VotingSongException;
import shook.shook.voting_song.exception.VotingSongPartException;

@RestControllerAdvice
public class VotingSongExceptionHandler {

    @ExceptionHandler({
        VotingSongPartException.PartForOtherSongException.class,
        PartException.DuplicateStartAndLengthException.class,
        VoteException.class,
    })
    public ResponseEntity<ErrorResponse> handleInternalVoteException(final CustomException e) {
        return ResponseEntity.internalServerError().body(ErrorResponse.from(e));
    }

    @ExceptionHandler({
        VotingSongException.class,
        VotingSongPartException.PartNotExistException.class,
        PartException.class
    })
    public ResponseEntity<ErrorResponse> handleVoteException(final CustomException e) {
        return ResponseEntity.badRequest().body(ErrorResponse.from(e));
    }
}
