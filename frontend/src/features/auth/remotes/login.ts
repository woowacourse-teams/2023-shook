import { client } from '@/shared/remotes/axios';

interface AccessTokenRes {
  accessToken: string;
}

export const getAccessToken = async (platform: string, code: string) => {
  const { data } = await client.get<AccessTokenRes>(`/login/${platform}`, {
    params: { code },
  });

  return data;
};
