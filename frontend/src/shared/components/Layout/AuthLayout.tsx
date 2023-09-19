import parseJWT from '@/features/auth/utils/parseJWT';
import AuthError from '@/shared/remotes/AuthError';
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

  // TODO: accessToken 유효하지 않은 경우
  if (!accessToken || !isValidToken(accessToken)) {
    // TODO: 인증 에러 발생했을 때 -> 전역상태 비워주기, login redirect
    console.log('layout 유효 X');
    throw new AuthError();
    return;
  }

  return <>{children}</>;
};

export default AuthLayout;
