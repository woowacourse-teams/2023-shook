package shook.shook.part.exception;

public class PartException extends RuntimeException {

    public static class StartLessThanZeroException extends PartException {

        public StartLessThanZeroException() {
            super();
        }
    }

    public static class StartOverSongLengthException extends PartException {

        public StartOverSongLengthException() {
            super();
        }
    }

    public static class EndOverSongLengthException extends PartException {

        public EndOverSongLengthException() {
            super();
        }
    }

    public static class InvalidLengthException extends PartException {

        public InvalidLengthException() {
            super();
        }
    }

    public static class DuplicateStartAndLengthException extends PartException {

        public DuplicateStartAndLengthException() {
            super();
        }
    }
}
