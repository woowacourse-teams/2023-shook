package shook.shook.song.exception.killingpart;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartException extends CustomException {

    public KillingPartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class PartNotExistException extends KillingPartException {

        public PartNotExistException() {
            super(ErrorCode.KILLING_PART_NOT_EXIST);
        }
    }

    public static class SongNotUpdatableException extends KillingPartException {

        public SongNotUpdatableException() {
            super(ErrorCode.KILLING_PART_SONG_NOT_UPDATABLE);
        }
    }

    public static class SongMaxKillingPartExceededException extends KillingPartException {

        public SongMaxKillingPartExceededException() {
            super(ErrorCode.SONG_MAX_KILLING_PART_EXCEEDED);
        }
    }
}
