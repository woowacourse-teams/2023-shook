package shook.shook.song.exception.killingpart;

public class KillingPartLikeException extends RuntimeException {

    public static class LikeForOtherKillingPartException extends KillingPartLikeException {

        public LikeForOtherKillingPartException() {
            super();
        }
    }

    public static class EmptyLikeException extends KillingPartLikeException {

        public EmptyLikeException() {
            super();
        }
    }

    public static class LikeStatusNotUpdatableException extends KillingPartLikeException {

        public LikeStatusNotUpdatableException() {
            super();
        }
    }
}
