import { useNavigate } from 'react-router-dom';
import ROUTE_PATH from '@/shared/constants/path';

export interface ErrorResponse {
  code: number;
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async (url: string, method: string, body?: unknown) => {
  // eslint-disable-next-line react-hooks/rules-of-hooks
  const navigate = useNavigate();
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
    navigate(ROUTE_PATH.LOGIN);
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
