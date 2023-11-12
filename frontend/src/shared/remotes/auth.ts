import { clientBasic } from './axios';

interface RefreshAccessTokenRes {
  accessToken: string;
}

export const postRefreshAccessToken = async (staleAccessToken: string) => {
  const { data } = await clientBasic.post<RefreshAccessTokenRes>('/reissue', staleAccessToken);

  return data;
};
