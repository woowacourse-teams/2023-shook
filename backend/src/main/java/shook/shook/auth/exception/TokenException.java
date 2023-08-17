package shook.shook.auth.exception;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public TokenException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class NotIssuedTokenException extends TokenException {

        public NotIssuedTokenException() {
            super(ErrorCode.NOT_ISSUED_TOKEN);
        }

        public NotIssuedTokenException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.NOT_ISSUED_TOKEN, inputValuesByProperty);
        }
    }

    public static class ExpiredTokenException extends TokenException {

        public ExpiredTokenException() {
            super(ErrorCode.EXPIRED_TOKEN);
        }

        public ExpiredTokenException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EXPIRED_TOKEN, inputValuesByProperty);
        }
    }
}
