package shook.shook.song.exception.legacy_killingpart;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class KillingPartCommentException extends CustomException {

    public KillingPartCommentException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public KillingPartCommentException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class NullOrEmptyPartCommentException extends KillingPartCommentException {

        public NullOrEmptyPartCommentException() {
            super(ErrorCode.EMPTY_KILLING_PART_COMMENT);
        }

        public NullOrEmptyPartCommentException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_KILLING_PART_COMMENT, inputValuesByProperty);
        }
    }

    public static class TooLongPartCommentException extends KillingPartCommentException {

        public TooLongPartCommentException() {
            super(ErrorCode.TOO_LONG_KILLING_PART_COMMENT);
        }

        public TooLongPartCommentException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_KILLING_PART_COMMENT, inputValuesByProperty);
        }
    }

    public static class CommentForOtherPartException extends KillingPartCommentException {

        public CommentForOtherPartException() {
            super(ErrorCode.KILLING_PART_COMMENT_FOR_OTHER_PART);
        }

        public CommentForOtherPartException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.KILLING_PART_COMMENT_FOR_OTHER_PART, inputValuesByProperty);
        }
    }

    public static class DuplicateCommentExistException extends KillingPartCommentException {

        public DuplicateCommentExistException() {
            super(ErrorCode.DUPLICATE_COMMENT_EXIST);
        }

        public DuplicateCommentExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.DUPLICATE_COMMENT_EXIST, inputValuesByProperty);
        }
    }
}
