import { useEffect } from 'react';
import { Navigate, useNavigate, useParams, useSearchParams } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import path from '@/shared/constants/path';
import accessTokenStorage from '@/shared/utils/accessTokenStorage';

interface AccessTokenResponse {
  accessToken: string;
}

const AuthPage = () => {
  const [searchParams] = useSearchParams();
  const { platform } = useParams();
  console.log('AuthPage_param_platform', platform);

  const { login } = useAuthContext();
  const navigate = useNavigate();

  // TODO: 예외처리
  const getAccessToken = async () => {
    const code = searchParams.get('code');

    if (!code) {
      accessTokenStorage.removeToken();
      navigate(path.LOGIN);
      alert('비정상적인 로그인입니다. 다시 로그인해주세요.');
      return;
    }

    console.log('AuthPage_fetch_url', `${process.env.BASE_URL}/login/${platform}?code=${code}`);
    const response = await fetch(`${process.env.BASE_URL}/login/${platform}?code=${code}`, {
      method: 'get',
      credentials: 'include',
    });

    const data = (await response.json()) as AccessTokenResponse;
    const { accessToken } = data;

    // accesstoken이 제대로 들어왔을 경
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
