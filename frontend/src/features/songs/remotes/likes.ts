import client from '@/shared/remotes/axios';

export const putKillingPartLikes = async (songId: number, partId: number, likeStatus: boolean) => {
  await client.put(`/songs/${songId}/parts/${partId}/likes`, { likeStatus });
};
