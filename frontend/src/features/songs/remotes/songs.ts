import fetcher from '@/shared/remotes';
import type { Genre } from '../types/Song.type';
import type { SongDetail, SongDetailEntries } from '@/shared/types/song';

export const getSongDetailEntries = async (
  songId: number,
  genre: Genre
): Promise<SongDetailEntries> => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;
  return await fetcher(`/songs/high-liked/${songId}${query}`, 'GET');
};

export const getExtraPrevSongDetails = async (
  songId: number,
  genre: Genre
): Promise<SongDetail[]> => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;
  return await fetcher(`/songs/high-liked/${songId}/prev${query}`, 'GET');
};

export const getExtraNextSongDetails = async (
  songId: number,
  genre: Genre
): Promise<SongDetail[]> => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;
  return await fetcher(`/songs/high-liked/${songId}/next${query}`, 'GET');
};
