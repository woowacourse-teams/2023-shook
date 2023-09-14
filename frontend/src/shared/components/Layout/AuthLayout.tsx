import { useNavigate } from 'react-router-dom';
import parseJWT from '@/features/auth/utils/parseJWT';
import ROUTE_PATH from '@/shared/constants/path';

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
  const accessToken = localStorage.getItem('userToken');
  const navigator = useNavigate();

  if (!accessToken || !isValidToken(accessToken)) {
    localStorage.removeItem('userToken');
    navigator(ROUTE_PATH.LOGIN);
    return;
  }

  return <>{children}</>;
};

export default AuthLayout;
