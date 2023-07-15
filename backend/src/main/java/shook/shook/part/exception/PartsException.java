package shook.shook.part.exception;

public class PartsException extends RuntimeException {

    public static class DuplicatePartExistException extends PartsException {

        public DuplicatePartExistException() {
            super();
        }
    }
}
