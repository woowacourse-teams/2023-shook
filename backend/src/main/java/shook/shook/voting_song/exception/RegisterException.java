package shook.shook.voting_song.exception;

public class RegisterException extends RuntimeException { // TODO: 2023/08/10 VoteException 으로 변경

    public static class VoteForOtherPartException extends RegisterException {

        public VoteForOtherPartException() {
            super();
        }
    }

    public static class DuplicateVoteExistException extends RegisterException {

        public DuplicateVoteExistException() {
            super();
        }
    }
}
