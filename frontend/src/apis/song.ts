import fetcher from '.';
import type { SongDetail } from '@/types/song';

export const getSongDetail = async (songId: number): Promise<SongDetail> => {
  return await fetcher<SongDetail>(`/songs/${songId}`, 'GET');
};
