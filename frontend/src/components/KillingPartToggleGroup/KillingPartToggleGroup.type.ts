import type { KILLING_PART_INTERVAL } from './constants';

export type KillingPartInterval =
  (typeof KILLING_PART_INTERVAL)[keyof typeof KILLING_PART_INTERVAL];
