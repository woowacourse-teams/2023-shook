package shook.shook.legacy.util;

import java.util.Objects;

public abstract class StringChecker {

    public static boolean isNullOrBlank(final String string) {
        return Objects.isNull(string) || string.isBlank();
    }
}
