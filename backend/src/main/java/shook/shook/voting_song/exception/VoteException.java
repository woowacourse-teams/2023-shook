package shook.shook.voting_song.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class VoteException extends CustomException {

    public VoteException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class VoteForOtherPartException extends VoteException {

        public VoteForOtherPartException() {
            super(ErrorCode.VOTE_FOR_OTHER_PART);
        }
    }

    public static class DuplicateVoteExistException extends VoteException {

        public DuplicateVoteExistException() {
            super(ErrorCode.DUPLICATE_VOTE_EXIST);
        }
    }
}
