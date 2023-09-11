package shook.shook.song.exception;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class SongException extends CustomException {

    public SongException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public SongException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class SongLengthLessThanOneException extends SongException {

        public SongLengthLessThanOneException() {
            super(ErrorCode.NOT_POSITIVE_SONG_LENGTH);
        }

        public SongLengthLessThanOneException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.NOT_POSITIVE_SONG_LENGTH, inputValuesByProperty);
        }
    }

    public static class SongNotExistException extends SongException {

        public SongNotExistException() {
            super(ErrorCode.SONG_NOT_EXIST);
        }

        public SongNotExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.SONG_NOT_EXIST, inputValuesByProperty);
        }
    }

    public static class NullOrEmptyTitleException extends SongException {

        public NullOrEmptyTitleException() {
            super(ErrorCode.EMPTY_SONG_TITLE);
        }

        public NullOrEmptyTitleException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_SONG_TITLE, inputValuesByProperty);
        }
    }

    public static class TooLongTitleException extends SongException {

        public TooLongTitleException() {
            super(ErrorCode.TOO_LONG_TITLE);
        }

        public TooLongTitleException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_TITLE, inputValuesByProperty);
        }
    }

    public static class NullOrEmptyVideoIdException extends SongException {

        public NullOrEmptyVideoIdException() {
            super(ErrorCode.EMPTY_VIDEO_ID);
        }

        public NullOrEmptyVideoIdException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_VIDEO_ID, inputValuesByProperty);
        }
    }

    public static class IncorrectVideoIdLengthException extends SongException {

        public IncorrectVideoIdLengthException() {
            super(ErrorCode.INCORRECT_VIDEO_ID_LENGTH);
        }

        public IncorrectVideoIdLengthException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.INCORRECT_VIDEO_ID_LENGTH, inputValuesByProperty);
        }
    }

    public static class NullOrEmptyImageUrlException extends SongException {

        public NullOrEmptyImageUrlException() {
            super(ErrorCode.EMPTY_SONG_IMAGE_URL);
        }

        public NullOrEmptyImageUrlException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_SONG_IMAGE_URL, inputValuesByProperty);
        }
    }

    public static class TooLongImageUrlException extends SongException {

        public TooLongImageUrlException() {
            super(ErrorCode.TOO_LONG_SONG_IMAGE_URL);
        }

        public TooLongImageUrlException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_SONG_IMAGE_URL, inputValuesByProperty);
        }
    }

    public static class NullOrEmptySingerNameException extends SongException {

        public NullOrEmptySingerNameException() {
            super(ErrorCode.EMPTY_SINGER_NAME);
        }

        public NullOrEmptySingerNameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_SINGER_NAME, inputValuesByProperty);
        }
    }

    public static class TooLongSingerNameException extends SongException {

        public TooLongSingerNameException() {
            super(ErrorCode.TOO_LONG_SINGER_NAME);
        }

        public TooLongSingerNameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_SINGER_NAME, inputValuesByProperty);
        }
    }

    public static class SongAlreadyExistException extends SongException {

        public SongAlreadyExistException() {
            super(ErrorCode.SONG_ALREADY_EXIST);
        }

        public SongAlreadyExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.SONG_ALREADY_EXIST, inputValuesByProperty);
        }
    }
}
