import fetcher from '.';
import type { KillingPartPostRequest, KillingPartPostResponse } from '@/types/killingPart';

export const postKillingPart = (
  songId: number,
  body: KillingPartPostRequest
): Promise<KillingPartPostResponse> => {
  return fetcher<KillingPartPostResponse>(`/songs/${songId}/parts`, 'POST', body);
};
