import type { KillingPartInterval } from '@/components/KillingPartToggleGroup';

export type PartVideoUrl = `https://www.youtube.com/embed/${string}?start=${number}&end=${number}`;

export interface KillingPartPostRequest {
  startSecond: number;
  length: KillingPartInterval;
}

export interface KillingPartPostResponse {
  rank: number;
  voteCount: number;
  partVideoUrl: PartVideoUrl;
}
