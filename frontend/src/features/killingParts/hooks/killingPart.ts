import fetcher from '@/shared/remotes';
import type { KillingPartPostRequest } from '@/shared/types/killingPart';

export const postKillingPart = async (songId: number, body: KillingPartPostRequest) => {
  return await fetcher(`/songs/${songId}/member-parts`, 'POST', body);
};
