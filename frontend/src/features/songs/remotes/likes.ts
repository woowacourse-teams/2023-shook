import fetcher from '@/shared/remotes';

export const putKillingPartLikes = async (songId: number, partId: number, likeStatus: boolean) => {
  return await fetcher(`/songs/${songId}/parts/${partId}/likes`, 'PUT', { likeStatus });
};
