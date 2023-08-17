export interface ErrorResponse {
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async <T>(url: string, method: string, body?: unknown): Promise<T> => {
  const options: RequestInit = {
    method,
    headers: {
      'Content-type': 'application/json',
      // TODO: Authorization
      // Authorization: localStorage.getItem('userToken') || '',
    },
  };

  if (body) {
    options.body = JSON.stringify(body);
  }

  const response = await fetch(`${BASE_URL}${url}`, options);

  if (response.status >= 500) {
    throw new Error(`서버문제로 HTTP 통신에 실패했습니다.`);
  }

  // TODO: 401 status -> login page
  // if(response.status === 401) {

  // }

  if (!response.ok) {
    const errorResponse: ErrorResponse = await response.json();

    throw errorResponse;
  }
  const data = await response.json();

  return data;
};

export default fetcher;
