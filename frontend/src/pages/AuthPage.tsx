import { useEffect } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import googleAuthUrl from '@/features/auth/constants/googleAuthUrl';
import accessTokenStorage from '@/shared/utils/accessTokenStorage';

interface AccessTokenResponse {
  accessToken: string;
}

const AuthPage = () => {
  const [searchParams] = useSearchParams();
  const { login } = useAuthContext();

  // TODO: 예외처리
  const getAccessToken = async () => {
    const code = searchParams.get('code');
    if (!code) {
      // TODO: throw error -> 401 인증에러
      accessTokenStorage.removeToken();
      window.location.href = googleAuthUrl;
      return;
    }

    const response = await fetch(`${process.env.BASE_URL}/login/google?code=${code}`, {
      method: 'get',
      credentials: 'include',
    });

    const data = (await response.json()) as AccessTokenResponse;
    const { accessToken } = data;

    // accesstoken이 제대로 들어왔을 경우
    if (accessToken) {
      login(accessToken);
    }
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  return <Navigate to="/" replace />;
};

export default AuthPage;
