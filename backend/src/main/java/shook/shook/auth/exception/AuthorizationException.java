package shook.shook.auth.exception;

public class AuthorizationException extends RuntimeException {

    public static class RefreshTokenNotFoundException extends AuthorizationException {

        public RefreshTokenNotFoundException() {
            super();
        }
    }

    public static class AccessTokenNotFoundException extends AuthorizationException {

        public AccessTokenNotFoundException() {
            super();
        }
    }

    public static class InvalidAuthorizationHeaderFormatException extends AuthorizationException {

        public InvalidAuthorizationHeaderFormatException() {
            super();
        }
    }

    public static class AuthContextException extends AuthorizationException {

        public AuthContextException() {
            super();
        }
    }
}
