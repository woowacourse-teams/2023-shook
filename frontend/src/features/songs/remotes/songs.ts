import fetcher from '@/shared/remotes';
import type { SongDetail, SongDetailEntries } from '@/shared/types/song';

export const getSongDetailEntries = async (songId: number): Promise<SongDetailEntries> => {
  return await fetcher(`/songs/${songId}`, 'GET');
};

export const getExtraPrevSongDetails = async (songId: number): Promise<SongDetail[]> => {
  return await fetcher(`/songs/${songId}/prev`, 'GET');
};

export const getExtraNextSongDetails = async (songId: number): Promise<SongDetail[]> => {
  return await fetcher(`/songs/${songId}/next`, 'GET');
};
