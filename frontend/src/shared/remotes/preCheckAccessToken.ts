import AuthError from '@/shared/remotes/AuthError';
import accessTokenStorage from '@/shared/utils/accessTokenStorage';
import type { ErrorResponse } from '@/shared/remotes/index';

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
      method: 'POST',
      headers: {
        Authorization: `Bearer ${accessToken}`,
      },
    });

    if (response.ok) {
      const { accessToken } = await response.json();
      accessTokenStorage.setToken(accessToken);
      return accessToken;
    }

    const errorResponse: ErrorResponse = await response.json();

    if (response.status === 401) {
      throw new AuthError(errorResponse);
    }
    // 기타 상태코드 처리
  }

  return null;
};

export default preCheckAccessToken;
