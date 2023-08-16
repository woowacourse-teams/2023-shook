import type { PartVideoUrl } from './killingPart';

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
}

export interface KillingPart {
  id: number;
  exist: true;
  rank: number;
  start: number;
  end: number;
  partVideoUrl: PartVideoUrl;
  voteCount: number;
  likeCount: number;
  likeStatus: boolean;
}
