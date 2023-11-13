import client from '@/shared/remotes/axios';
import type { Genre } from '../types/Song.type';
import type { SongDetail, SongDetailEntries } from '@/shared/types/song';

export const getSongDetailEntries = async (songId: number, genre: Genre) => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;
  const { data } = await client.get<SongDetailEntries>(`/songs/high-liked/${songId}${query}`);

  return data;
};

export const getExtraPrevSongDetails = async (songId: number, genre: Genre) => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;
  const { data } = await client.get<SongDetail[]>(`/songs/high-liked/${songId}/prev${query}`);

  return data;
};

export const getExtraNextSongDetails = async (songId: number, genre: Genre) => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;
  const { data } = await client.get<SongDetail[]>(`/songs/high-liked/${songId}/next${query}`);

  return data;
};
