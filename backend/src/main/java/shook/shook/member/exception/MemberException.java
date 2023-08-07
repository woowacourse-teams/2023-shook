package shook.shook.member.exception;

public class MemberException extends RuntimeException {

    public static class NullOrEmptyEmailException extends MemberException {

        public NullOrEmptyEmailException() {
            super();
        }
    }

    public static class TooLongEmailException extends MemberException {

        public TooLongEmailException() {
            super();
        }
    }

    public static class InValidEmailFormException extends MemberException {

        public InValidEmailFormException() {
            super();
        }
    }

    public static class NullOrEmptyNicknameException extends MemberException {

        public NullOrEmptyNicknameException() {
            super();
        }
    }

    public static class TooLongNicknameException extends MemberException {

        public TooLongNicknameException() {
            super();
        }
    }

    public static class ExistMemberException extends MemberException {

        public ExistMemberException() {
            super();
        }
    }
}
