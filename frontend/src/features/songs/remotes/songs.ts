import fetcher from '@/shared/remotes';
import type { SongDetailEntries } from '@/shared/types/song';

export const getSongDetailEntries = async (songId: number): Promise<SongDetailEntries> => {
  return await fetcher(`/songs/${songId}`, 'GET');
};
