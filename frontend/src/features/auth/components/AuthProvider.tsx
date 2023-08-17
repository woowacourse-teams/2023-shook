import { createContext, useContext, useMemo, useState } from 'react';
import parseJWT from '../utils/parseJWT';

interface User {
  memberId: number;
  nickname: string;
}

interface AuthContextProps {
  user: User | null;
  login: (accessToken: string) => void;
}

export const useAuthContext = () => {
  const contextValue = useContext(AuthContext);

  if (contextValue === null) throw new Error('AuthContext가 null입니다.');

  return contextValue;
};

const AuthContext = createContext<AuthContextProps | null>(null);

const AuthProvider = ({ children }: { children: React.ReactElement[] }) => {
  const [accessToken, setAccessToken] = useState(localStorage.getItem('userToken') || '');

  // TODO: 예외처리?
  const user: User | null = useMemo(() => {
    if (!accessToken) {
      return null;
    }

    const { memberId, nickname } = parseJWT(accessToken);

    return {
      memberId,
      nickname,
    };
  }, [accessToken]);

  const login = (userToken: string) => {
    localStorage.setItem('userToken', userToken);
    setAccessToken(userToken);
  };

  return <AuthContext.Provider value={{ user, login }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;