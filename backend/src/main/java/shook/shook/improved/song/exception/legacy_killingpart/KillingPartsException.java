package shook.shook.improved.song.exception.legacy_killingpart;

import java.util.Map;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

public class KillingPartsException extends CustomException {

    public KillingPartsException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public KillingPartsException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class OutOfSizeException extends KillingPartsException {

        public OutOfSizeException() {
            super(ErrorCode.KILLING_PARTS_OUT_OF_SIZE);
        }

        public OutOfSizeException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.KILLING_PARTS_OUT_OF_SIZE, inputValuesByProperty);
        }
    }

    public static class EmptyKillingPartsException extends KillingPartsException {

        public EmptyKillingPartsException() {
            super(ErrorCode.EMPTY_KILLING_PARTS);
        }
    }
}
