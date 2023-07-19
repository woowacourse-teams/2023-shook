import type { KillingPartInterval } from '@/components/KillingPartToggleGroup';

export type PartUrl = `https://www.youtube.com/embed/${string}?start=${number}&end=${number}`;

export interface KillingPartPostRequest {
  start: number;
  length: KillingPartInterval;
}

export interface KillingPartPostResponse {
  rank: number;
  partUrl: PartUrl;
}
