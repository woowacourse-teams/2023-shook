package shook.shook.song.exception.killingpart;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartLikeException extends CustomException {

    public KillingPartLikeException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public KillingPartLikeException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class LikeForOtherKillingPartException extends KillingPartLikeException {

        public LikeForOtherKillingPartException() {
            super(ErrorCode.EMPTY_LIKE_EXCEPTION);
        }

        public LikeForOtherKillingPartException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.LIKE_FOR_OTHER_KILLING_PART, inputValuesByProperty);
        }
    }

    public static class EmptyLikeException extends KillingPartLikeException {

        public EmptyLikeException() {
            super(ErrorCode.EMPTY_LIKE_EXCEPTION);
        }
    }
}
