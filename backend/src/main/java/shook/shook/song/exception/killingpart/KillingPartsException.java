package shook.shook.song.exception.killingpart;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartsException extends CustomException {

    public KillingPartsException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class OutOfSizeException extends KillingPartException {

        public OutOfSizeException() {
            super(ErrorCode.KILLING_PARTS_OUT_OF_SIZE);
        }
    }

    public static class EmptyKillingPartsException extends KillingPartException {

        public EmptyKillingPartsException() {
            super(ErrorCode.EMPTY_KILLING_PARTS);
        }
    }
}
