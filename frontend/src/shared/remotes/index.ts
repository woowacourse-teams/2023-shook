import ROUTE_PATH from '@/shared/constants/path';

export interface ErrorResponse {
  code: number;
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async (url: string, method: string, body?: unknown) => {
  const loginRedirectUrl = `${process.env.BASE_URL}${ROUTE_PATH.LOGIN}`?.replace(/api\/?/, '');

  const accessToken = localStorage.getItem('userToken');

  const headers: Record<string, string> = {
    'Content-type': 'application/json',
  };

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

  if (response.status >= 500) {
    throw new Error(`서버문제로 HTTP 통신에 실패했습니다.`);
  }

  if (response.status === 401) {
    localStorage.removeItem('userToken');
    //TODO: 해당 부분 router-dom으로 해결 가능한지 확인해야함.
    window.location.href = loginRedirectUrl;
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
