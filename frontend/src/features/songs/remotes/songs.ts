import client from '@/shared/remotes/axios';
import type { Genre } from '../types/Song.type';
import type { SongDetail, SongDetailEntries } from '@/shared/types/song';

export const getSongDetailEntries = async (songId: number, genre: Genre) => {
  const { data } = await client.get<SongDetailEntries>(`/songs/high-liked/${songId}`, {
    params: { genre: genre === 'ALL' ? null : genre },
  });

  return data;
};

export const getExtraPrevSongDetails = async (songId: number, genre: Genre) => {
  const { data } = await client.get<SongDetail[]>(`/songs/high-liked/${songId}/prev`, {
    params: { genre: genre === 'ALL' ? null : genre },
  });

  return data;
};

export const getExtraNextSongDetails = async (songId: number, genre: Genre) => {
  const { data } = await client.get<SongDetail[]>(`/songs/high-liked/${songId}/next`, {
    params: { genre: genre === 'ALL' ? null : genre },
  });

  return data;
};
