package shook.shook.auth.ui;

public enum MemberStatus {

    MEMBER,
    ANONYMOUS;

    public boolean isAnonymous() {
        return this == ANONYMOUS;
    }
}
