package shook.shook.auth.exception;

public class TokenException extends RuntimeException {

    public static class NotIssuedTokenException extends TokenException {

        public NotIssuedTokenException() {
            super();
        }
    }

    public static class ExpiredTokenException extends TokenException {

        public ExpiredTokenException() {
            super();
        }
    }

    public static class TokenNotFoundException extends TokenException {

        public TokenNotFoundException() {
            super();
        }
    }
}