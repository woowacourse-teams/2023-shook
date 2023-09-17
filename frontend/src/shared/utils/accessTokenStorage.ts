import parseJWT from '@/features/auth/utils/parseJWT';
interface AccessTokenPayload {
  sub: string;
  memberId: number;
  nickname: string;
  iat: number;
  exp: number;
}

const ACCESS_TOKEN_KEY = 'userToken';

const accessTokenStorage = {
  getTokenWithPayload() {
    // token을 가져온다.
    const accessToken = this.getToken();
    //1. token이 null일 경우
    if (!accessToken) {
      this.removeToken();
      return null;
    }

    //3. token의 유효성을 검사한다.
    try {
      return {
        accessToken,
        payload: this.parseToken(accessToken) as AccessTokenPayload,
      };
    } catch {
      this.removeToken();
      return null;
    }
  },

  getToken() {
    return localStorage.getItem(ACCESS_TOKEN_KEY);
  },
  removeToken() {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
  },

  // jwt parsing을 시도하고 에러가 발생할 경우 false를 반환한다.
  parseToken(token: string) {
    return parseJWT(token);
  },
};

export default accessTokenStorage;
