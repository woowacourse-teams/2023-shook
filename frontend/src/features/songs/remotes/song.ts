import fetcher from '@/shared/remotes';
import type { SongDetail } from '@/shared/types/song';

export const getSongDetail = async (songId: number): Promise<SongDetail> => {
  return await fetcher<SongDetail>(`/songs/${songId}`, 'GET');
};
