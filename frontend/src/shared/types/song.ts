import type { Genre } from '@/features/songs/types/Song.type';

export interface SongDetailEntries {
  prevSongs: SongDetail[];
  currentSong: SongDetail;
  nextSongs: SongDetail[];
}

export interface SongDetail {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoId: string;
  albumCoverUrl: string;
  killingParts: KillingPart[];
  memberPart: KillingPart;
}

export interface KillingPart {
  id: number;
  rank: number;
  start: number;
  end: number;
  voteCount: number;
  likeCount: number;
  partVideoUrl: string;
  partLength: number;
  likeStatus: boolean;
}

// TODO: songDetail type과 다른 점...?
export interface SongInfo {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoId: string;
  albumCoverUrl: string;
  genre: Genre;
  killingParts: KillingPart[];
}
