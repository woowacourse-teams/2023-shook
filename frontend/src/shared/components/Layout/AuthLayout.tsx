import { useNavigate } from 'react-router-dom';
import parseJWT from '@/features/auth/utils/parseJWT';
import ROUTE_PATH from '@/shared/constants/path';
import accessTokenStorage from '@/shared/utils/accessTokenStorage';

const isValidToken = (accessToken: string) => {
  if (!accessToken) return false;

  const { memberId, nickname } = parseJWT(accessToken);

  // TODO: memberId와 url param Id 가 다를 때 다르게 처리해줄 것.
  if (!memberId || !nickname || typeof memberId !== 'number' || typeof nickname !== 'string') {
    return false;
  }

  return true;
};

const AuthLayout = ({ children }: { children: React.ReactElement }) => {
  const accessToken = accessTokenStorage.getToken();
  const navigator = useNavigate();

  // TODO: accessToken 유효하지 않은 경우
  if (!accessToken || !isValidToken(accessToken)) {
    // TODO: 인증 에러 발생했을 때 -> 전역상태 비워주기, login redirect
    accessTokenStorage.removeToken();
    navigator(ROUTE_PATH.LOGIN);
    return;
  }

  return <>{children}</>;
};

export default AuthLayout;
