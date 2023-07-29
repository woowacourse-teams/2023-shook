import type { PartVideoUrl } from './killingPart';

type VideoUrl = `https://www.youtube.com/embed/${string}`;

export interface SongDetail {
  id: number;
  title: string;
  singer: string;
  videoLength: number;
  songVideoUrl: VideoUrl;
  killingParts: KillingPart[];
}

type KillingPart = {
  exist: true;
  rank: number;
  start: number;
  end: number;
  partVideoUrl: PartVideoUrl;
  voteCount: number;
};
