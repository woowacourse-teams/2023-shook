import AuthError from '@/shared/remotes/AuthError';
import preCheckAccessToken from '@/shared/remotes/preCheckAccessToken';

export interface ErrorResponse {
  code: number;
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async (url: string, method: string, body?: unknown) => {
  const headers: Record<string, string> = {
    'Content-type': 'application/json',
  };

  const accessToken = await preCheckAccessToken();

  if (accessToken) {
    headers['Authorization'] = `Bearer ${accessToken}`;
  }

  const options: RequestInit = {
    method,
    headers,
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  const response = await fetch(`${BASE_URL}${url}`, options);

  if (!response.ok) {
    const errorResponse: ErrorResponse = await response.json();

    if (response.status >= 500) {
      // TODO: Error 객체
      throw new Error(errorResponse.message);
    }

    if (response.status === 401) {
      // TODO: 인증 에러 발생했을 때 -> localstorage비워주기, 전역상태 비워주기, login redirect
      console.log('in Fetcher, 401 error');
      throw new AuthError(errorResponse);
    }

    // TODO: Error 객체
    throw new Error(errorResponse.message);
  }

  const contentType = response.headers.get('content-type');
  if (!contentType || !contentType.includes('application/json')) return response;

  return response.json();
};

export default fetcher;
