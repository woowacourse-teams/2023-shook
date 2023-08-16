package shook.shook.song.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class SongException extends CustomException {

    public SongException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class SongLengthLessThanOneException extends SongException {

        public SongLengthLessThanOneException() {
            super(ErrorCode.NOT_POSITIVE_SONG_LENGTH);
        }
    }

    public static class SongNotExistException extends SongException {

        public SongNotExistException() {
            super(ErrorCode.SONG_NOT_EXIST);
        }
    }

    public static class NullOrEmptyTitleException extends SongException {

        public NullOrEmptyTitleException() {
            super(ErrorCode.EMPTY_SONG_TITLE);
        }
    }

    public static class TooLongTitleException extends SongException {

        public TooLongTitleException() {
            super(ErrorCode.TOO_LONG_TITLE);
        }
    }

    public static class NullOrEmptyVideoUrlException extends SongException {

        public NullOrEmptyVideoUrlException() {
            super(ErrorCode.EMPTY_VIDEO_URL);
        }
    }

    public static class TooLongVideoUrlException extends SongException {

        public TooLongVideoUrlException() {
            super(ErrorCode.TOO_LONG_VIDEO_URL);
        }
    }

    public static class NullOrEmptyImageUrlException extends SongException {

        public NullOrEmptyImageUrlException() {
            super(ErrorCode.EMPTY_SONG_IMAGE_URL);
        }
    }

    public static class TooLongImageUrlException extends SongException {

        public TooLongImageUrlException() {
            super(ErrorCode.TOO_LONG_SONG_IMAGE_URL);
        }
    }

    public static class NullOrEmptySingerNameException extends SongException {

        public NullOrEmptySingerNameException() {
            super(ErrorCode.EMPTY_SINGER_NAME);
        }
    }

    public static class TooLongSingerNameException extends SongException {

        public TooLongSingerNameException() {
            super(ErrorCode.TOO_LONG_SINGER_NAME);
        }
    }
}
