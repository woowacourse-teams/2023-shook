package shook.shook.auth.application;

public interface OAuthInfoProvider {

    String getMemberInfo(final String accessToken);

    String getAccessToken(final String authorizationCode);
}
