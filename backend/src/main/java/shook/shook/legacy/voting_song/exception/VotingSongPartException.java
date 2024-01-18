package shook.shook.legacy.voting_song.exception;

import java.util.Map;
import shook.shook.legacy.globalexception.CustomException;
import shook.shook.legacy.globalexception.ErrorCode;

public class VotingSongPartException extends CustomException {

    public VotingSongPartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public VotingSongPartException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class PartNotExistException extends VotingSongPartException {

        public PartNotExistException() {
            super(ErrorCode.VOTING_SONG_PART_NOT_EXIST);
        }

        public PartNotExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTING_SONG_PART_NOT_EXIST, inputValuesByProperty);
        }
    }

    public static class PartForOtherSongException extends VotingSongPartException {

        public PartForOtherSongException() {
            super(ErrorCode.VOTING_SONG_PART_FOR_OTHER_SONG);
        }

        public PartForOtherSongException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTING_SONG_PART_FOR_OTHER_SONG, inputValuesByProperty);
        }
    }
}
