package shook.shook.part.domain;

public enum PartLength {
    SHORT(5),
    STANDARD(10),
    LONG(15);

    private final int value;

    PartLength(final int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
