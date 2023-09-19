import accessTokenStorage from '@/shared/utils/accessTokenStorage';
import { isExpiredAfter60seconds } from '@/shared/utils/fetchUtils';

export interface ErrorResponse {
  code: number;
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async (url: string, method: string, body?: unknown) => {
  const headers: Record<string, string> = {
    'Content-type': 'application/json',
  };

  const accessTokenWithPayload = accessTokenStorage.getTokenWithPayload();

  if (accessTokenWithPayload) {
    const {
      accessToken,
      payload: { exp },
    } = accessTokenWithPayload;

    headers['Authorization'] = `Bearer ${accessToken}`;

    if (isExpiredAfter60seconds(exp)) {
      const response = await fetch(`${BASE_URL}/reissue`, {
        headers,
        method: 'GET',
      });

      accessTokenStorage.removeToken();
      delete headers.Authorization;

      if (response.ok) {
        const { accessToken } = await response.json();
        accessTokenStorage.setToken(accessToken);
        headers['Authorization'] = `Bearer ${accessToken}`;
      }
    }
  }

  const options: RequestInit = {
    method,
    headers,
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  const response = await fetch(`${BASE_URL}${url}`, options);

  if (response.status >= 500) {
    throw new Error(`서버문제로 HTTP 통신에 실패했습니다.`);
  }

  if (response.status === 401) {
    accessTokenStorage.removeToken();
    throw new Error(`인증 에러`);
    // TODO: 인증 에러 발생했을 때 -> localstorage비워주기, 전역상태 비워주기, login redirect
  }

  if (!response.ok) {
    const errorResponse: ErrorResponse = await response.json();

    throw errorResponse;
  }

  const contentType = response.headers.get('content-type');
  if (!contentType || !contentType.includes('application/json')) return response;

  return response.json();
};

export default fetcher;
