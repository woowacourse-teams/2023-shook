package shook.shook.song.exception.killingpart;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartLikeException extends CustomException {

    public KillingPartLikeException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class LikeForOtherKillingPartException extends KillingPartLikeException {

        public LikeForOtherKillingPartException() {
            super(ErrorCode.LIKE_FOR_OTHER_KILLING_PART);
        }
    }

    public static class EmptyLikeException extends KillingPartLikeException {

        public EmptyLikeException() {
            super(ErrorCode.EMPTY_LIKE_EXCEPTION);
        }
    }
}
