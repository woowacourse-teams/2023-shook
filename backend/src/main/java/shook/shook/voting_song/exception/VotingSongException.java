package shook.shook.voting_song.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class VotingSongException extends CustomException {

    public VotingSongException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class VotingSongNotExistException extends VotingSongException {

        public VotingSongNotExistException() {
            super(ErrorCode.VOTING_SONG_NOT_EXIST);
        }
    }
}
