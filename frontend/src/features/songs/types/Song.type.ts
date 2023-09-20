import type GENRE from '@/features/songs/constants/genre';

export type Genre = keyof typeof GENRE;

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
