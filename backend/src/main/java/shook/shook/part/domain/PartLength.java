package shook.shook.part.domain;

import java.util.Arrays;
import java.util.Map;
import shook.shook.part.exception.PartException;

public enum PartLength {
    SHORT(5),
    STANDARD(10),
    LONG(15);

    private final int value;

    PartLength(final int value) {
        this.value = value;
    }

    public static PartLength findBySecond(final int second) {
        return Arrays.stream(values())
            .filter(length -> length.value == second)
            .findFirst()
            .orElseThrow(() -> new PartException.InvalidLengthException(
                Map.of("PartLength", String.valueOf(second))
            ));
    }

    public int getEndSecond(final int start) {
        return start + this.value;
    }

    public int getValue() {
        return value;
    }
}
