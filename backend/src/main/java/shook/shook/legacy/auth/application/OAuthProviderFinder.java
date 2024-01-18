package shook.shook.legacy.auth.application;

import jakarta.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OAuthProviderFinder {

    private final Map<OAuthType, OAuthInfoProvider> oauthExecution = new HashMap<>();
    private final KakaoInfoProvider kakaoInfoProvider;
    private final GoogleInfoProvider googleInfoProvider;

    @PostConstruct
    public void init() {
        oauthExecution.put(OAuthType.GOOGLE, googleInfoProvider);
        oauthExecution.put(OAuthType.KAKAO, kakaoInfoProvider);
    }

    public OAuthInfoProvider getOAuthInfoProvider(final String oauthType) {
        final OAuthType key = OAuthType.find(oauthType);
        return oauthExecution.get(key);
    }
}
