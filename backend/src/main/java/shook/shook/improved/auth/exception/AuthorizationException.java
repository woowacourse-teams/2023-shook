package shook.shook.improved.auth.exception;

import java.util.Map;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

public class AuthorizationException extends CustomException {

    public AuthorizationException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public AuthorizationException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class RefreshTokenNotFoundException extends AuthorizationException {

        public RefreshTokenNotFoundException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.REFRESH_TOKEN_NOT_FOUND_EXCEPTION, inputValuesByProperty);
        }

        public RefreshTokenNotFoundException() {
            super(ErrorCode.REFRESH_TOKEN_NOT_FOUND_EXCEPTION);
        }
    }

    public static class AccessTokenNotFoundException extends AuthorizationException {

        public AccessTokenNotFoundException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.ACCESS_TOKEN_NOT_FOUND, inputValuesByProperty);
        }

        public AccessTokenNotFoundException() {
            super(ErrorCode.ACCESS_TOKEN_NOT_FOUND);
        }
    }

    public static class UnauthenticatedException extends AuthorizationException {

        public UnauthenticatedException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.UNAUTHENTICATED_EXCEPTION, inputValuesByProperty);
        }

        public UnauthenticatedException() {
            super(ErrorCode.UNAUTHENTICATED_EXCEPTION);
        }
    }
}
