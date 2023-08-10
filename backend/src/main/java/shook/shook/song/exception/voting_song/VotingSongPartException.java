package shook.shook.song.exception.voting_song;

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
