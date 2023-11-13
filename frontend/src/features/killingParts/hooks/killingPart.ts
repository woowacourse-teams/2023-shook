import client from '@/shared/remotes/axios';
import type { KillingPartPostRequest } from '@/shared/types/killingPart';

export const postKillingPart = async (songId: number, body: KillingPartPostRequest) => {
  await client.post(`/songs/${songId}/member-parts`, body);
};
