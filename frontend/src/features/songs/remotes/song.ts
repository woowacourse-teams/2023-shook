import fetcher from '@/shared/remotes';
import type { Song } from '../types/Song.type';
import type { SongDetail } from '@/shared/types/song';

export const getSongDetail = async (songId: number): Promise<SongDetail> => {
  return await fetcher(`/songs/${songId}`, 'GET');
};

export const getHighLikedSongs = async (): Promise<Song[]> => {
  return await fetcher('/songs/high-liked', 'GET');
};
