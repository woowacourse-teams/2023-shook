package shook.shook.legacy.status.response;

import java.util.Arrays;

public class ProfileResponse {

    private final String profile;

    public ProfileResponse(final String[] profiles) {
        this.profile = findReservedProfile(profiles);
    }

    private String findReservedProfile(final String[] profiles) {
        return Arrays.stream(profiles)
            .filter(ReservedProfiles::isExist)
            .findFirst()
            .orElse(profiles[0]);
    }

    public String getProfiles() {
        return profile;
    }
}
