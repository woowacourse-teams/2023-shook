package shook.shook.auth.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class AuthorizationException extends CustomException {

    public AuthorizationException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class RefreshTokenNotFoundException extends AuthorizationException {

        public RefreshTokenNotFoundException() {
            super(ErrorCode.REFRESH_TOKEN_NOT_FOUND_EXCEPTION);
        }
    }

    public static class AccessTokenNotFoundException extends AuthorizationException {

        public AccessTokenNotFoundException() {
            super(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
        }
    }
}
