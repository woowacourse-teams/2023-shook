import fetcher from '.';
import type { KillingPartPostRequest, KillingPartPostResponse } from '@/types/killingPart';

export const postKillingPart = async (
  songId: number,
  body: KillingPartPostRequest
): Promise<KillingPartPostResponse> => {
  return await fetcher<KillingPartPostResponse>(`/songs/${songId}/parts`, 'POST', body);
};
