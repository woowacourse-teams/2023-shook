package shook.shook.part.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class PartException extends CustomException {

    public PartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class StartLessThanZeroException extends PartException {

        public StartLessThanZeroException() {
            super(ErrorCode.VOTING_PART_START_LESS_THAN_ZERO);
        }
    }

    public static class StartOverSongLengthException extends PartException {

        public StartOverSongLengthException() {
            super(ErrorCode.VOTING_PART_START_OVER_SONG_LENGTH);
        }
    }

    public static class EndOverSongLengthException extends PartException {

        public EndOverSongLengthException() {
            super(ErrorCode.VOTING_PART_END_OVER_SONG_LENGTH);
        }
    }

    public static class InvalidLengthException extends PartException {

        public InvalidLengthException() {
            super(ErrorCode.INVALID_VOTING_PART_LENGTH);
        }
    }

    public static class DuplicateStartAndLengthException extends PartException {

        public DuplicateStartAndLengthException() {
            super(ErrorCode.VOTING_PART_DUPLICATE_START_AND_LENGTH_EXCEPTION);
        }
    }
}
