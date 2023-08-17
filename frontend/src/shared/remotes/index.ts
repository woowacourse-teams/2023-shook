import googleAuthUrl from '@/features/auth/constants/googleAuthUrl';

export interface ErrorResponse {
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async <T>(url: string, method: string, body?: unknown): Promise<T> => {
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
    window.location.href = googleAuthUrl;
  }

  if (!response.ok) {
    const errorResponse: ErrorResponse = await response.json();

    throw errorResponse;
  }
  const data = await response.json();

  return data;
};

export default fetcher;
