package shook.shook.part.exception;

public class VoteException extends RuntimeException {

    public static class VoteForOtherPartException extends VoteException {

        public VoteForOtherPartException() {
            super();
        }
    }

    public static class DuplicateVoteExistException extends VoteException {

        public DuplicateVoteExistException() {
            super();
        }
    }
}
