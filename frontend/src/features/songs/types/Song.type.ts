import type GENRES from '@/features/songs/constants/genres';

export type Genre = keyof typeof GENRES;

export interface Song {
  id: number;
  title: string;
  singer: string;
  albumCoverUrl: string;
  totalLikeCount: number;
  genre: Genre;
}

export interface VotingSong {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  albumCoverUrl: string;
}
