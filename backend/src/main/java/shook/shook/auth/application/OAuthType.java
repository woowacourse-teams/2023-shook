package shook.shook.auth.application;

import java.util.Arrays;
import shook.shook.auth.exception.OAuthException;

public enum OAuthType {

    GOOGLE, KAKAO;

    public static OAuthType find(final String oauthType) {
        return Arrays.stream(OAuthType.values())
            .filter(type -> type.name().equals(oauthType.toUpperCase()))
            .findFirst()
            .orElseThrow(OAuthException.OauthTypeNotFoundException::new);
    }
}
