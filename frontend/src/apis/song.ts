import fetcher from '.';
import type { SongDetail } from '@/types/song';

export const getSongDetail = (songId: number): Promise<SongDetail> => {
  return fetcher<SongDetail>(`/songs/${songId}`, 'GET');
};
