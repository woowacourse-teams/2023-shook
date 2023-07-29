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

    public static class NullOrEmptyNickNameException extends MemberException {

        public NullOrEmptyNickNameException() {
            super();
        }
    }

    public static class TooLongNickNameException extends MemberException {

        public TooLongNickNameException() {
            super();
        }
    }
}
