package shook.shook.legacy.voting_song.exception;

import java.util.Map;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

public class VotingSongException extends CustomException {

    public VotingSongException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public VotingSongException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class VotingSongNotExistException extends VotingSongException {

        public VotingSongNotExistException() {
            super(ErrorCode.VOTING_SONG_NOT_EXIST);
        }

        public VotingSongNotExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTING_SONG_NOT_EXIST, inputValuesByProperty);
        }
    }
}