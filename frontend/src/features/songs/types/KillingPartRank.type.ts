import type KILLING_PART_RANK from '../constants/killingPartRank';

export type KillingPartRank = (typeof KILLING_PART_RANK)[keyof typeof KILLING_PART_RANK];
