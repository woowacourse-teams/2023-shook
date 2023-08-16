import { useEffect } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';

interface AccessTokenResponse {
  accessToken: string;
}

const AuthPage = () => {
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  const getAccessToken = async () => {
    const response = await fetch(`${process.env.BASE_URL}/login/google?code=${code}`, {
      method: 'get',
    });
    const data = (await response.json()) as AccessTokenResponse;
    const { accessToken } = data;
    localStorage.setItem('userToken', accessToken);
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  return <Navigate to="/" />;
};

export default AuthPage;
