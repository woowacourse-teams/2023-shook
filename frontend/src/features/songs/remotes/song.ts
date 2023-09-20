import fetcher from '@/shared/remotes';
import type { Genre, Song } from '../types/Song.type';
import type { SongDetail } from '@/shared/types/song';

export const getSongDetail = async (songId: number): Promise<SongDetail> => {
  return await fetcher(`/songs/${songId}`, 'GET');
};

export const getHighLikedSongs = async (genre?: Genre): Promise<Song[]> => {
  const query = genre ? `?genre=${genre}` : '';

  return await fetcher(`/songs/high-liked${query}`, 'GET');
};
