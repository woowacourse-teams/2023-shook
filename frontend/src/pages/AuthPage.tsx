import { useEffect } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';

interface AccessTokenResponse {
  accessToken: string;
}

const AuthPage = () => {
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');
  const { login } = useAuthContext();

  // TODO: 예외처리
  const getAccessToken = async () => {
    const response = await fetch(`${process.env.BASE_URL}/login/google?code=${code}`, {
      method: 'get',
      credentials: 'include',
    });
    const data = (await response.json()) as AccessTokenResponse;
    const { accessToken } = data;

    login(accessToken);
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  return <Navigate to="/" />;
};

export default AuthPage;
