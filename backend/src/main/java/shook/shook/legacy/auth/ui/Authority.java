package shook.shook.legacy.auth.ui;

public enum Authority {

    MEMBER,
    ANONYMOUS;

    public boolean isAnonymous() {
        return this == ANONYMOUS;
    }
}
