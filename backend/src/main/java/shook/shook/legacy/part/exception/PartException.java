package shook.shook.legacy.part.exception;

import java.util.Map;
import shook.shook.legacy.globalexception.CustomException;
import shook.shook.legacy.globalexception.ErrorCode;

public class PartException extends CustomException {

    public PartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public PartException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class StartLessThanZeroException extends PartException {

        public StartLessThanZeroException() {
            super(ErrorCode.VOTING_PART_START_LESS_THAN_ZERO);
        }

        public StartLessThanZeroException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTING_PART_START_LESS_THAN_ZERO, inputValuesByProperty);
        }
    }

    public static class StartOverSongLengthException extends PartException {

        public StartOverSongLengthException() {
            super(ErrorCode.VOTING_PART_START_OVER_SONG_LENGTH);
        }

        public StartOverSongLengthException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTING_PART_START_OVER_SONG_LENGTH, inputValuesByProperty);
        }
    }

    public static class EndOverSongLengthException extends PartException {

        public EndOverSongLengthException() {
            super(ErrorCode.VOTING_PART_END_OVER_SONG_LENGTH);
        }

        public EndOverSongLengthException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTING_PART_END_OVER_SONG_LENGTH, inputValuesByProperty);
        }
    }

    public static class InvalidLengthException extends PartException {

        public InvalidLengthException() {
            super(ErrorCode.INVALID_VOTING_PART_LENGTH);
        }

        public InvalidLengthException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.INVALID_VOTING_PART_LENGTH, inputValuesByProperty);
        }
    }

    public static class DuplicateStartAndLengthException extends PartException {

        public DuplicateStartAndLengthException() {
            super(ErrorCode.VOTING_PART_DUPLICATE_START_AND_LENGTH_EXCEPTION);
        }

        public DuplicateStartAndLengthException(final Map<String, String> inputValuesByProperty) {

            super(ErrorCode.VOTING_PART_DUPLICATE_START_AND_LENGTH_EXCEPTION,
                  inputValuesByProperty);
        }
    }
}
