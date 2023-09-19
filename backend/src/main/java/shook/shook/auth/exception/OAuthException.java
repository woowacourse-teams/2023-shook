package shook.shook.auth.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class OAuthException extends CustomException {

    public OAuthException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class InvalidAccessTokenException extends OAuthException {

        public InvalidAccessTokenException() {
            super(ErrorCode.INVALID_ACCESS_TOKEN);
        }
    }

    public static class InvalidAuthorizationCodeException extends OAuthException {

        public InvalidAuthorizationCodeException() {
            super(ErrorCode.INVALID_AUTHORIZATION_CODE);
        }
    }

    public static class GoogleServerException extends OAuthException {

        public GoogleServerException() {
            super(ErrorCode.GOOGLE_SERVER_EXCEPTION);
        }
    }

    public static class KakaoServerException extends OAuthException {

        public KakaoServerException() {
            super(ErrorCode.GOOGLE_SERVER_EXCEPTION);
        }
    }
}
