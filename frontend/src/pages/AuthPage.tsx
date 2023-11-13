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

  // TODO: 함수 네이밍을 변경해야 합니다.
  // 제안: 'code' param 여부 + 분기는 함수 외부로 빼는게 어떤가요?
  // 분리한다면 함수 네이밍도 쉬워질 것 같아요.
  const getAccessToken1 = async () => {
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
    getAccessToken1();
  }, []);

  return <Navigate to="/" replace />;
};

export default AuthPage;
