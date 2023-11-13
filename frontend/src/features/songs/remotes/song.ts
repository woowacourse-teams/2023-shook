import client from '@/shared/remotes/axios';
import type { Genre, Song } from '../types/Song.type';
import type { RecentSong } from '@/shared/types/song';

// 메인 케러셀 최신순 노래 n개 조회 api - 쿼리파람 없는경우, 응답 기본값은 5개입니다.
export const getRecentSongs = async (songCount?: number) => {
  const query = songCount ? `?size=${songCount}` : '';

  const { data } = await client.get<RecentSong[]>(`/songs/recent${query}`);

  return data;
};

export const getHighLikedSongs = async (genre: Genre) => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;

  const { data } = await client.get<Song[]>(`/songs/high-liked${query}`);

  return data;
};
