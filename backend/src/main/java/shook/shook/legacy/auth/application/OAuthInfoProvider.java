package shook.shook.legacy.auth.application;

public interface OAuthInfoProvider {

    String getMemberInfo(final String accessToken);

    String getAccessToken(final String authorizationCode);
}
