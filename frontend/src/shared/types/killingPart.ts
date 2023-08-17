import type { KillingPartInterval } from '@/features/songs/types/KillingPartToggleGroup.type';

export type PartVideoUrl = `https://youtu.be/${string}?start=${number}&end=${number}`;

export interface KillingPartPostRequest {
  startSecond: number;
  length: KillingPartInterval;
}

export interface KillingPartPostResponse {
  rank: number;
  voteCount: number;
  partVideoUrl: PartVideoUrl;
}
