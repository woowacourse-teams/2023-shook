import type { PartVideoUrl } from './killingPart';

type VideoUrl = `https://www.youtube.com/embed/${string}`;

export interface SongDetail {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoUrl: VideoUrl;
  albumCoverUrl: string;
  killingParts: KillingPart[];
}

export interface KillingPart {
  id: number;
  rank: number;
  likeCount: number;
  start: number;
  end: number;
  partVideoUrl: string;
  partLength: number;
  likeStatus: boolean;
}

export interface SongVoting {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoId: string;
  albumCoverUrl: string;
  killingParts: KillingPart[];
}

export interface VotingSongList {
  prevSongs: SongVoting[];
  currentSong: SongVoting;
  nextSongs: SongVoting[];
}
