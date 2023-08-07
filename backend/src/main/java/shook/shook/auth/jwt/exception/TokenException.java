package shook.shook.auth.jwt.exception;

public class TokenException extends RuntimeException {

    public static class NotIssuedTokenException extends TokenException {

        public NotIssuedTokenException() {
            super();
        }
    }
}
