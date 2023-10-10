package shook.shook.my_part.exception;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class MemberPartException extends CustomException {

    public MemberPartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberPartException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class MemberPartStartSecondNegativeException extends MemberPartException {

        public MemberPartStartSecondNegativeException() {
            super(ErrorCode.NEGATIVE_START_SECOND);
        }

        public MemberPartStartSecondNegativeException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.NEGATIVE_START_SECOND, inputValuesByProperty);
        }
    }

    public static class MemberPartEndOverSongLengthException extends MemberPartException {

        public MemberPartEndOverSongLengthException() {
            super(ErrorCode.NEGATIVE_START_SECOND);
        }

        public MemberPartEndOverSongLengthException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.NEGATIVE_START_SECOND, inputValuesByProperty);
        }
    }
}
