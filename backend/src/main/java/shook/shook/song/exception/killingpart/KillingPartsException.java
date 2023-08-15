package shook.shook.song.exception.killingpart;

public class KillingPartsException {

    public static class OutOfSizeException extends KillingPartException {

        public OutOfSizeException() {
            super();
        }
    }

    public static class EmptyKillingPartException extends KillingPartException {

        public EmptyKillingPartException() {
            super();
        }
    }
}
