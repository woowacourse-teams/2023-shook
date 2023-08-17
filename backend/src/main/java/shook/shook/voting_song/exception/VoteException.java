package shook.shook.voting_song.exception;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class VoteException extends CustomException {

    public VoteException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public VoteException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class VoteForOtherPartException extends VoteException {

        public VoteForOtherPartException() {
            super(ErrorCode.VOTE_FOR_OTHER_PART);
        }

        public VoteForOtherPartException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.VOTE_FOR_OTHER_PART, inputValuesByProperty);
        }
    }

    public static class DuplicateVoteExistException extends VoteException {

        public DuplicateVoteExistException() {
            super(ErrorCode.DUPLICATE_VOTE_EXIST);
        }

        public DuplicateVoteExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.DUPLICATE_VOTE_EXIST, inputValuesByProperty);
        }
    }
}
