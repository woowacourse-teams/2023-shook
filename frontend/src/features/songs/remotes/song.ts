import fetcher from '@/shared/remotes';
import type { Genre, Song } from '../types/Song.type';
import type { RecentSong } from '@/shared/types/song';

// 메인 케러셀 최신순 노래 n개 조회 api - 쿼리파람 없는경우, 응답 기본값은 5개입니다.
export const getRecentSongs = async (songCount?: number): Promise<RecentSong[]> => {
  const query = songCount ? `?size=${songCount}` : '';

  return await fetcher(`/songs/recent${query}`, 'GET');
};

export const getHighLikedSongs = async (genre: Genre): Promise<Song[]> => {
  const query = genre === 'ALL' ? '' : `?genre=${genre}`;

  return await fetcher(`/songs/high-liked${query}`, 'GET');
};
