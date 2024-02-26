package shook.shook.improved.auth.exception;

import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

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

    public static class OauthTypeNotFoundException extends OAuthException {

        public OauthTypeNotFoundException() {
            super(ErrorCode.NOT_FOUND_OAUTH);
        }
    }
}
