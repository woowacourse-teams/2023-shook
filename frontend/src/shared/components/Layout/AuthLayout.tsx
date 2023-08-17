import googleAuthUrl from '@/features/auth/constants/googleAuthUrl';
import parseJWT from '@/features/auth/utils/parseJWT';

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

  if (!accessToken || !isValidToken(accessToken)) {
    console.log(isValidToken(accessToken!));
    localStorage.removeItem('userToken');
    window.location.href = `${googleAuthUrl}`;
    return;
  }

  return <>{children}</>;
};

export default AuthLayout;
