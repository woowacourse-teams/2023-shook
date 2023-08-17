package shook.shook.member.exception;

import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class MemberException extends CustomException {

    public MemberException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public static class NullOrEmptyEmailException extends MemberException {

        public NullOrEmptyEmailException() {
            super(ErrorCode.EMPTY_EMAIL);
        }
    }

    public static class TooLongEmailException extends MemberException {

        public TooLongEmailException() {
            super(ErrorCode.TOO_LONG_EMAIL);
        }
    }

    public static class InValidEmailFormException extends MemberException {

        public InValidEmailFormException() {
            super(ErrorCode.INVALID_EMAIL_FORM);
        }
    }

    public static class NullOrEmptyNicknameException extends MemberException {

        public NullOrEmptyNicknameException() {
            super(ErrorCode.EMPTY_NICKNAME);
        }
    }

    public static class TooLongNicknameException extends MemberException {

        public TooLongNicknameException() {
            super(ErrorCode.TOO_LONG_NICKNAME);
        }
    }

    public static class ExistMemberException extends MemberException {

        public ExistMemberException() {
            super(ErrorCode.EXIST_MEMBER);
        }
    }

    public static class MemberNotExistException extends MemberException {

        public MemberNotExistException() {
            super(ErrorCode.MEMBER_NOT_EXIST);
        }
    }
}
