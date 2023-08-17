import { useParams } from 'react-router-dom';
import googleAuthUrl from '@/features/auth/constants/googleAuthUrl';
import parseJWT from '@/features/auth/utils/parseJWT';

const isValidToken = (accessToken: string, id: number) => {
  if (!accessToken) return false;

  const { memberId, nickname } = parseJWT(accessToken);

  // TODO: memberId와 url param Id 가 다를 때 다르게 처리해줄 것.
  if (
    !memberId ||
    !nickname ||
    typeof memberId !== 'number' ||
    typeof nickname !== 'string' ||
    memberId !== id
  ) {
    return false;
  }

  return true;
};

const AuthLayout = ({ children }: { children: React.ReactElement }) => {
  const accessToken = localStorage.getItem('userToken');
  const { id } = useParams();

  if (!accessToken || !isValidToken(accessToken, Number(id))) {
    localStorage.removeItem('userToken');
    window.location.href = `${googleAuthUrl}`;
    return;
  }

  return <>{children}</>;
};

export default AuthLayout;
