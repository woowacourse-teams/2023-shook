package shook.shook.legacy.status.response;

import java.util.Arrays;
import java.util.Optional;

public enum ReservedProfiles {
    PROD1,
    PROD2,
    DEV,
    LOCAL,
    TEST;

    public static boolean isExist(final String target) {
        final Optional<String> findProfile = Arrays.stream(values())
            .map(ReservedProfiles::name)
            .filter(valueName -> valueName.equals(target.toUpperCase()))
            .findAny();
        return findProfile.isPresent();
    }
}
