package shook.shook.auth.oauth.exception;

public class OAuthException extends RuntimeException {

    public static class InvalidEmailException extends OAuthException {

        public InvalidEmailException() {
            super();
        }
    }

    public static class InvalidAccessTokenException extends OAuthException {

        public InvalidAccessTokenException() {
            super();
        }
    }

    public static class InvalidAuthorizationCodeException extends OAuthException {

        public InvalidAuthorizationCodeException() {
            super();
        }
    }

    public static class GoogleServerException extends OAuthException {

        public GoogleServerException() {
            super();
        }
    }
}
