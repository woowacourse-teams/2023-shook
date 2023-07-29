package shook.shook.song.exception;

public class UnregisteredSongException extends RuntimeException {

    public static class EmptyResultException extends UnregisteredSongException {

        public EmptyResultException() {
            super();
        }
    }

    public static class ManiaDBServerException extends UnregisteredSongException {

        public ManiaDBServerException() {
            super();
        }
    }

    public static class ManiaDBClientException extends UnregisteredSongException {

        public ManiaDBClientException() {
            super();
        }
    }
}
