import { createContext, useContext, useState } from 'react';

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
  setUser: React.Dispatch<React.SetStateAction<User | null>>;
}

const AuthContext = createContext<AuthContextProps | null>(null);

export const useAuth = () => {
  const authValues = useContext(AuthContext);

  if (!authValues) {
    throw new Error('AuthContext에 value가 제공되지 않았습니다.');
  }

  const { user, setUser } = authValues;

  const login = (accessToken: string) => {
    localStorage.setItem('userToken', accessToken);

    // TODO: nickname 추가
    const { memberId } = parseJWT(accessToken);

    const user: User = {
      memberId,
      // TODO: nickname 받아서 쓸 것
      nickname: 'ukko',
    };

    setUser(user);
  };

  return {
    user,
    login,
  };
};

const AuthProvider = ({ children }: { children: React.ReactElement[] }) => {
  const [user, setUser] = useState<User | null>(null);

  return <AuthContext.Provider value={{ user, setUser }}>{children}</AuthContext.Provider>;
};

export default AuthProvider;
