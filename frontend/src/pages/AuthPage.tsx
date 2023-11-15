import { useEffect } from 'react';
import { Navigate, useNavigate, useSearchParams } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { getAccessToken } from '@/features/auth/remotes/auth';
import path from '@/shared/constants/path';
import useValidParams from '@/shared/hooks/useValidParams';
import accessTokenStorage from '@/shared/utils/accessTokenStorage';

const AuthPage = () => {
  const [searchParams] = useSearchParams();
  const { platform } = useValidParams();

  const { login } = useAuthContext();
  const navigate = useNavigate();

  const authLogin = async () => {
    const code = searchParams.get('code');

    if (!code) {
      accessTokenStorage.removeToken();
      navigate(path.LOGIN);
      alert('비정상적인 로그인입니다. 다시 로그인해주세요.');
      return;
    }

    const { accessToken } = await getAccessToken(platform, code);

    if (accessToken) {
      login(accessToken);
    }
  };

  useEffect(() => {
    authLogin();
  }, []);

  return <Navigate to="/" replace />;
};

export default AuthPage;
