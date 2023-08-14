package shook.shook.voting_song.exception;

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
