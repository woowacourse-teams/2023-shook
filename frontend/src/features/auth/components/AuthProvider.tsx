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
  const [accessToken, setAccessToken] = useState(
    'eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ1c2VyIiwibWVtYmVySWQiOjEsImlhdCI6MTY5MjE3NDkyOCwiZXhwIjoxNjkyMTc0OTQwfQ.88DaWR1XRPEQVe_SOtCar060_EmTNINriRNU3zfKfsU'
  );

  const login = (userToken: string) => {
    localStorage.setItem('userToken', userToken);
    setAccessToken(userToken);
  };

  let user: User | null = useMemo(() => {
    const { memberId } = parseJWT(accessToken);

    return {
      memberId,
      nickname: 'ukko',
    };
  }, [accessToken]);

  user = null;

  return <AuthContext.Provider value={{ user, login }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
