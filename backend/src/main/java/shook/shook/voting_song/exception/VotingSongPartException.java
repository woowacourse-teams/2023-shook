package shook.shook.voting_song.exception;

public class VotingSongPartException extends RuntimeException {

    public static class PartNotExistException extends VotingSongPartException {

        public PartNotExistException() {
            super();
        }
    }

    public static class PartForOtherSongException extends VotingSongPartException {

        public PartForOtherSongException() {
            super();
        }
    }
}
