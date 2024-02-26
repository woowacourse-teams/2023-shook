package shook.shook.improved.part.part_comment.exception;

import java.util.Map;
import shook.shook.improved.globalexception.CustomException;
import shook.shook.improved.globalexception.ErrorCode;

public class PartCommentException extends CustomException {

    public PartCommentException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public PartCommentException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class NullOrEmptyPartCommentException extends PartCommentException {

        public NullOrEmptyPartCommentException() {
            super(ErrorCode.EMPTY_KILLING_PART_COMMENT);
        }

        public NullOrEmptyPartCommentException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_KILLING_PART_COMMENT, inputValuesByProperty);
        }
    }

    public static class TooLongPartCommentException extends PartCommentException {

        public TooLongPartCommentException() {
            super(ErrorCode.TOO_LONG_KILLING_PART_COMMENT);
        }

        public TooLongPartCommentException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_KILLING_PART_COMMENT, inputValuesByProperty);
        }
    }

    public static class CommentForOtherPartException extends PartCommentException {

        public CommentForOtherPartException() {
            super(ErrorCode.KILLING_PART_COMMENT_FOR_OTHER_PART);
        }

        public CommentForOtherPartException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.KILLING_PART_COMMENT_FOR_OTHER_PART, inputValuesByProperty);
        }
    }

    public static class DuplicateCommentExistException extends PartCommentException {

        public DuplicateCommentExistException() {
            super(ErrorCode.DUPLICATE_COMMENT_EXIST);
        }

        public DuplicateCommentExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.DUPLICATE_COMMENT_EXIST, inputValuesByProperty);
        }
    }
}
