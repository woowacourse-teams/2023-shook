import parseJWT from '@/features/auth/utils/parseJWT';

const ACCESS_TOKEN_KEY = 'userToken';
interface AccessTokenPayload {
  sub: string;
  memberId: number;
  nickname: string;
  iat: number;
  exp: number;
}

interface AccessCache {
  accessToken: string;
  payload: AccessTokenPayload;
}

// 은닉하기 위해 객체 밖으로 빼어냈습니다.
let cache: AccessCache | null;

const accessTokenStorage = {
  getTokenWithPayload() {
    const accessToken = this.getToken();

    try {
      if (!accessToken) throw new Error();

      if (this.isCacheMissed(accessToken)) {
        this.setCache(accessToken);
      }

      if (cache) {
        return {
          ...cache,
        };
      }
    } catch {
      this.removeToken();
      return null;
    }
  },

  getToken() {
    return localStorage.getItem(ACCESS_TOKEN_KEY);
  },

  setToken(token: string) {
    localStorage.setItem(ACCESS_TOKEN_KEY, token);
  },

  removeToken() {
    localStorage.removeItem(ACCESS_TOKEN_KEY);
  },

  parseToken(token: string) {
    return parseJWT(token);
  },

  isCacheMissed(accessToken: string) {
    return !cache || cache.accessToken !== accessToken;
  },

  setCache(accessToken: string) {
    cache = {
      accessToken: accessToken,
      payload: this.parseToken(accessToken) as AccessTokenPayload,
    };
  },
};

export default accessTokenStorage;
