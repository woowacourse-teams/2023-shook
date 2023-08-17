import fetcher from '@/shared/remotes';
import type { KillingPartPostRequest } from '@/shared/types/killingPart';

export const postKillingPart = async (songId: number, body: KillingPartPostRequest) => {
  return await fetcher(`/voting-songs/${songId}/parts`, 'POST', body);
};
