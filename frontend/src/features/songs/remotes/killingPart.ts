import fetcher from '@/shared/remotes';
import type { KillingPartPostRequest, KillingPartPostResponse } from '@/shared/types/killingPart';

export const postKillingPart = async (
  songId: number,
  body: KillingPartPostRequest
): Promise<KillingPartPostResponse> => {
  return await fetcher(`/songs/${songId}/parts`, 'POST', body);
};
