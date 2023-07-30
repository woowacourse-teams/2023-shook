package shook.shook.auth.jwt.exception;

public class TokenException extends RuntimeException {

    public static class InValidTokenException extends TokenException {

        public InValidTokenException() {
            super();
        }
    }
}
