package shook.shook.song.exception.killingpart;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartException extends CustomException {

    public KillingPartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public KillingPartException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class PartNotExistException extends KillingPartException {

        public PartNotExistException() {
            super(ErrorCode.KILLING_PART_NOT_EXIST);
        }

        public PartNotExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.KILLING_PART_NOT_EXIST, inputValuesByProperty);
        }
    }

    public static class SongNotUpdatableException extends KillingPartException {

        public SongNotUpdatableException() {
            super(ErrorCode.KILLING_PART_SONG_NOT_UPDATABLE);
        }

        public SongNotUpdatableException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.KILLING_PART_SONG_NOT_UPDATABLE, inputValuesByProperty);
        }
    }

    public static class SongMaxKillingPartExceededException extends KillingPartException {

        public SongMaxKillingPartExceededException() {
            super(ErrorCode.SONG_MAX_KILLING_PART_EXCEEDED);
        }

        public SongMaxKillingPartExceededException(
            final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.SONG_MAX_KILLING_PART_EXCEEDED, inputValuesByProperty);
        }
    }
}
