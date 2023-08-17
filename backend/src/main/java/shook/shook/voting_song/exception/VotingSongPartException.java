package shook.shook.voting_song.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class VotingSongPartException extends CustomException {

    public VotingSongPartException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class PartNotExistException extends VotingSongPartException {

        public PartNotExistException() {
            super(ErrorCode.VOTING_SONG_PART_NOT_EXIST);
        }
    }

    public static class PartForOtherSongException extends VotingSongPartException {

        public PartForOtherSongException() {
            super(ErrorCode.VOTING_SONG_PART_FOR_OTHER_SONG);
        }
    }
}
