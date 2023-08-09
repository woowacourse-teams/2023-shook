package shook.shook.song.exception;

public class SongException extends RuntimeException {

    public static class SongLengthLessThanOneException extends SongException {

        public SongLengthLessThanOneException() {
            super();
        }
    }

    public static class SongNotExistException extends SongException {

        public SongNotExistException() {
            super();
        }
    }

    public static class NullOrEmptyTitleException extends SongException {

        public NullOrEmptyTitleException() {
            super();
        }
    }

    public static class TooLongTitleException extends SongException {

        public TooLongTitleException() {
            super();
        }
    }

    public static class NullOrEmptyVideoUrlException extends SongException {

        public NullOrEmptyVideoUrlException() {
            super();
        }
    }

    public static class TooLongVideoUrlException extends SongException {

        public TooLongVideoUrlException() {
            super();
        }
    }

    public static class NullOrEmptyImageUrlException extends SongException {

        public NullOrEmptyImageUrlException() {
            super();
        }
    }

    public static class TooLongImageUrlException extends SongException {

        public TooLongImageUrlException() {
            super();
        }
    }

    public static class NullOrEmptySingerNameException extends SongException {

        public NullOrEmptySingerNameException() {
            super();
        }
    }

    public static class TooLongSingerNameException extends SongException {

        public TooLongSingerNameException() {
            super();
        }
    }
}
