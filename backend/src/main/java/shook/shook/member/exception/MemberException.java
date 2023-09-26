package shook.shook.member.exception;

import java.util.Map;
import shook.shook.globalexception.CustomException;
import shook.shook.globalexception.ErrorCode;

public class MemberException extends CustomException {

    public MemberException(final ErrorCode errorCode) {
        super(errorCode);
    }

    public MemberException(
        final ErrorCode errorCode,
        final Map<String, String> inputValuesByProperty
    ) {
        super(errorCode, inputValuesByProperty);
    }

    public static class NullOrEmptyEmailException extends MemberException {

        public NullOrEmptyEmailException() {
            super(ErrorCode.EMPTY_EMAIL);
        }

        public NullOrEmptyEmailException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_EMAIL, inputValuesByProperty);
        }
    }

    public static class TooLongEmailException extends MemberException {

        public TooLongEmailException() {
            super(ErrorCode.TOO_LONG_EMAIL);
        }

        public TooLongEmailException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_EMAIL, inputValuesByProperty);
        }
    }

    public static class InValidEmailFormException extends MemberException {

        public InValidEmailFormException() {
            super(ErrorCode.INVALID_EMAIL_FORM);
        }

        public InValidEmailFormException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.INVALID_EMAIL_FORM, inputValuesByProperty);
        }
    }

    public static class NullOrEmptyNicknameException extends MemberException {

        public NullOrEmptyNicknameException() {
            super(ErrorCode.EMPTY_NICKNAME);
        }

        public NullOrEmptyNicknameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EMPTY_NICKNAME, inputValuesByProperty);
        }
    }

    public static class TooLongNicknameException extends MemberException {

        public TooLongNicknameException() {
            super(ErrorCode.TOO_LONG_NICKNAME);
        }

        public TooLongNicknameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.TOO_LONG_NICKNAME, inputValuesByProperty);
        }
    }

    public static class ExistMemberException extends MemberException {

        public ExistMemberException() {
            super(ErrorCode.EXIST_MEMBER);
        }

        public ExistMemberException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.EXIST_MEMBER, inputValuesByProperty);
        }
    }

    public static class MemberNotExistException extends MemberException {

        public MemberNotExistException() {
            super(ErrorCode.MEMBER_NOT_EXIST);
        }

        public MemberNotExistException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.MEMBER_NOT_EXIST, inputValuesByProperty);
        }
    }

    public static class ExistNicknameException extends MemberException {

        public ExistNicknameException() {
            super(ErrorCode.ALREADY_EXIST_NICKNAME);
        }

        public ExistNicknameException(final Map<String, String> inputValuesByProperty) {
            super(ErrorCode.ALREADY_EXIST_NICKNAME, inputValuesByProperty);
        }
    }
}
