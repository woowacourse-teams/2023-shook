package shook.shook.song.exception.voting_song;

public class VotingSongException extends RuntimeException {

    public static class VotingSongNotExistException extends VotingSongException {

        public VotingSongNotExistException() {
            super();
        }
    }
}
