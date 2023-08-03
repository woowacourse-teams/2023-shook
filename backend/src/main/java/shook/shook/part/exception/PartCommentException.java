package shook.shook.part.exception;

public class PartCommentException extends RuntimeException {

    public static class NullOrEmptyPartCommentException extends PartCommentException {

        public NullOrEmptyPartCommentException() {
            super();
        }
    }

    public static class TooLongPartCommentException extends PartCommentException {

        public TooLongPartCommentException() {
            super();
        }
    }

    public static class CommentForOtherPartException extends PartCommentException {

        public CommentForOtherPartException() {
            super();
        }
    }

    public static class DuplicateCommentExistException extends PartCommentException {

        public DuplicateCommentExistException() {
            super();
        }
    }
}
