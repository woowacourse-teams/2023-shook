import { createContext, useContext, useMemo, useState } from 'react';

const parseJWT = (token: string) => {
  const payloadUrl = token.split('.')[1];
  const base64 = payloadUrl.replace(/-/g, '+').replace(/_/g, '/');
  const jsonPayload = decodeURIComponent(
    window
      .atob(base64)
      .split('')
      .map((c) => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
      .join('')
  );

  return JSON.parse(jsonPayload);
};

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

  console.log('>>> user:', user);

  return <AuthContext.Provider value={{ user, login }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
