import { client, clientBasic } from '@/shared/remotes/axios';
import type { AccessTokenRes } from '../types/auth.type';

export const getAccessToken = async (platform: string, code: string) => {
  const { data } = await client.get<AccessTokenRes>(`/login/${platform}`, {
    params: { code },
  });

  return data;
};

export const postRefreshAccessToken = async () => {
  const { data } = await clientBasic.post<AccessTokenRes>('/reissue');

  return data;
};
