/* eslint-disable prefer-const */
import axios from 'axios';
import { postRefreshAccessToken } from '@/features/auth/remotes/auth';
import type { AccessTokenRes } from '@/features/auth/types/auth.type';
import type { AxiosError, AxiosRequestConfig, InternalAxiosRequestConfig } from 'axios';

const { BASE_URL } = process.env;

const defaultConfig: AxiosRequestConfig = {
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
};

const clientBasic = axios.create(defaultConfig);
const client = axios.create(defaultConfig);

// 요청 인터셉터
const setToken = (config: InternalAxiosRequestConfig) => {
  const accessToken = localStorage.getItem('userToken');

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
};

// 응답 에러 인터셉터
let reissuePromise: Promise<AccessTokenRes> | null = null;

const reissueOnExpiredTokenError = async (error: AxiosError) => {
  const originalRequest = error.config;
  const isAuthError = error.response?.status === 401;
  const hasAuthorization = !!originalRequest?.headers.Authorization;

  if (isAuthError && hasAuthorization) {
    try {
      const { accessToken } = await (reissuePromise ??= reissue());

      originalRequest.headers.Authorization = `Bearer ${accessToken}`;

      return client(originalRequest);
    } catch (error) {
      return Promise.reject(error);
    } finally {
      reissuePromise = null;
    }
  }

  return Promise.reject(error);
};

const reissue = async () => {
  try {
    const response = await postRefreshAccessToken();
    const { accessToken } = response;

    localStorage.setItem('userToken', accessToken);
    return response;
  } catch (error) {
    window.alert('세션이 만료되었습니다. 다시 로그인 해주세요');
    localStorage.removeItem('userToken');
    window.location.href = '/login';

    throw error;
  }
};

clientBasic.interceptors.request.use(setToken);
client.interceptors.request.use(setToken);
client.interceptors.response.use((response) => response, reissueOnExpiredTokenError);

export { clientBasic, client };
