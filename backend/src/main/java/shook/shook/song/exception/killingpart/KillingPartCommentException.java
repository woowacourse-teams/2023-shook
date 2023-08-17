package shook.shook.song.exception.killingpart;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartCommentException extends CustomException {

    public KillingPartCommentException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class NullOrEmptyPartCommentException extends KillingPartCommentException {

        public NullOrEmptyPartCommentException() {
            super(ErrorCode.EMPTY_KILLING_PART_COMMENT);
        }
    }

    public static class TooLongPartCommentException extends KillingPartCommentException {

        public TooLongPartCommentException() {
            super(ErrorCode.TOO_LONG_KILLING_PART_COMMENT);
        }
    }

    public static class CommentForOtherPartException extends KillingPartCommentException {

        public CommentForOtherPartException() {
            super(ErrorCode.KILLING_PART_COMMENT_FOR_OTHER_PART);
        }
    }

    public static class DuplicateCommentExistException extends KillingPartCommentException {

        public DuplicateCommentExistException() {
            super(ErrorCode.DUPLICATE_COMMENT_EXIST);
        }
    }
}
