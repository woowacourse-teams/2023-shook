package shook.shook.song.exception;

public class UnregisteredSongException extends RuntimeException {

    public static class EmptyResultException extends UnregisteredSongException {

        public EmptyResultException() {
            super();
        }
    }
}
