import axios from 'axios';
import { postRefreshAccessToken } from './auth';
import type { AxiosError, AxiosRequestConfig, InternalAxiosRequestConfig } from 'axios';

const { BASE_URL } = process.env;

const defaultConfig: AxiosRequestConfig = {
  baseURL: BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
  withCredentials: true,
};

export const clientBasic = axios.create(defaultConfig);
export const client = axios.create(defaultConfig);

// 요청 인터셉터
const setToken = (config: InternalAxiosRequestConfig) => {
  const accessToken = localStorage.getItem('userToken');

  if (accessToken) {
    config.headers.Authorization = `Bearer ${accessToken}`;
  }

  return config;
};

// 응답 인터셉터
const refreshAccessTokenOnAuthError = async (error: AxiosError) => {
  const config = error.config;

  if (error.response?.status === 401 && config?.headers.Authorization) {
    try {
      const staleAccessToken = localStorage.getItem('userToken') ?? '';
      const { accessToken } = await postRefreshAccessToken(staleAccessToken);

      localStorage.setItem('userToken', accessToken);
      config.headers.Authorization = `Bearer ${accessToken}`;

      return client(config);
    } catch {
      // window.alert('세션이 만료되었습니다. 다시 로그인 해주세요');
    }
  }

  return Promise.reject(error);
};

client.interceptors.request.use(setToken);
client.interceptors.response.use((response) => response, refreshAccessTokenOnAuthError);
