package shook.shook.song.exception.killingpart;

public class KillingPartException extends RuntimeException {

    public static class PartNotExistException extends KillingPartException {

        public PartNotExistException() {
            super();
        }
    }
}
