package shook.shook.auth.jwt.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class TokenException extends CustomException {

    public TokenException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class NotIssuedTokenException extends TokenException {

        public NotIssuedTokenException() {
            super(ErrorCode.NOT_ISSUED_TOKEN);
        }
    }

    public static class ExpiredTokenException extends TokenException {

        public ExpiredTokenException() {
            super(ErrorCode.EXPIRED_TOKEN);
        }
    }
}
