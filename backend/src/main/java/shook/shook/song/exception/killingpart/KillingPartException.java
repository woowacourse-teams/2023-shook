package shook.shook.song.exception.killingpart;

public class KillingPartException extends RuntimeException {

    public static class PartNotExistException extends KillingPartException {

        public PartNotExistException() {
            super();
        }
    }

    public static class SongNotUpdatableException extends KillingPartException {

        public SongNotUpdatableException() {
            super();
        }
    }

    public static class SongMaxKillingPartExceededException extends KillingPartException {

        public SongMaxKillingPartExceededException() {
            super();
        }
    }
}
