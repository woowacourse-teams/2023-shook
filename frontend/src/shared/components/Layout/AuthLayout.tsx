import { Navigate } from 'react-router-dom';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import parseJWT from '@/features/auth/utils/parseJWT';
import path from '@/shared/constants/path';
import accessTokenStorage from '@/shared/utils/accessTokenStorage';
import type React from 'react';

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
  const { logout } = useAuthContext();

  if (!accessToken || !isValidToken(accessToken)) {
    logout();
    return <Navigate to={path.LOGIN} />;
  }

  return <>{children}</>;
};

export default AuthLayout;
