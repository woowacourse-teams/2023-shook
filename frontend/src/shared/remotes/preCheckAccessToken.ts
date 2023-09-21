import accessTokenStorage from '@/shared/utils/accessTokenStorage';

const isTokenExpiredAfter60seconds = (tokenExp: number) => {
  return tokenExp * 1000 - 30 * 1000 < Date.now();
};

const preCheckAccessToken = async () => {
  const accessTokenWithPayload = accessTokenStorage.getTokenWithPayload();

  if (accessTokenWithPayload) {
    const {
      accessToken,
      payload: { exp },
    } = accessTokenWithPayload;

    if (!isTokenExpiredAfter60seconds(exp)) {
      return accessToken;
    }

    const response = await fetch(`${process.env.BASE_URL}/reissue`, {
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });

    if (response.ok) {
      const { accessToken } = await response.json();
      accessTokenStorage.setToken(accessToken);
      return accessToken;
    }

    accessTokenStorage.removeToken();
  }

  return null;
};

export default preCheckAccessToken;
