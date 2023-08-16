import { useEffect } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';

interface AccessTokenResponse {
  accessToken: string;
}

const AuthPage = () => {
  const [searchParams] = useSearchParams();
  const code = searchParams.get('code');

  const getAccessToken = async () => {
    const response = await fetch(`http://192.168.1.86:8080/login/google?code=${code}`, {
      method: 'get',
    });
    const data = (await response.json()) as AccessTokenResponse;
    // accessToken을 바탕으로 userId와 userNickname을 추출할 수 있다.
    // accessToken은 localStorage에 저장한다.
    // userId와 userNickname은 전역으로 저장한다.
    const { accessToken } = data;
    console.log(data);
    localStorage.setItem('userToken', accessToken);
  };

  useEffect(() => {
    getAccessToken();
  }, []);

  return <div>hello</div>;
};

export default AuthPage;
