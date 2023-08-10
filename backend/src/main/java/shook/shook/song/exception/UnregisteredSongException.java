package shook.shook.song.exception;

public class UnregisteredSongException extends RuntimeException {

    public static class NullOrBlankSearchWordException extends UnregisteredSongException {

        public NullOrBlankSearchWordException() {
            super();
        }
    }

}
