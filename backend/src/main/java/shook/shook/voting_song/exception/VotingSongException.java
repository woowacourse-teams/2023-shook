package shook.shook.voting_song.exception;

public class VotingSongException extends RuntimeException {

    public static class VotingSongNotExistException extends VotingSongException {

        public VotingSongNotExistException() {
            super();
        }
    }
}
