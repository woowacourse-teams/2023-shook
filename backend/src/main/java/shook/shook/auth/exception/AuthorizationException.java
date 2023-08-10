package shook.shook.auth.exception;

public class AuthorizationException extends RuntimeException{

    public static class TokenNotFoundException extends AuthorizationException {

        public TokenNotFoundException() {
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
