export interface ErrorResponse {
  message: string;
}

const { BASE_URL } = process.env;

const fetcher = async (url: string, method: string, body?: unknown) => {
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

  const contentType = response.headers.get('content-type');
  if (!contentType || !contentType.includes('application/json')) return response;

  return response.json();
};

export default fetcher;
