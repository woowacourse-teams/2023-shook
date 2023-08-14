package shook.shook.song.exception.killingpart;

public class KillingPartCommentException extends RuntimeException {

    public static class NullOrEmptyPartCommentException extends KillingPartCommentException {

        public NullOrEmptyPartCommentException() {
            super();
        }
    }

    public static class TooLongPartCommentException extends KillingPartCommentException {

        public TooLongPartCommentException() {
            super();
        }
    }

    public static class CommentForOtherPartException extends KillingPartCommentException {

        public CommentForOtherPartException() {
            super();
        }
    }

    public static class DuplicateCommentExistException extends KillingPartCommentException {

        public DuplicateCommentExistException() {
            super();
        }
    }
}
