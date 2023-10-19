export type PartVideoUrl = `https://youtu.be/${string}?start=${number}&end=${number}`;

export interface KillingPartPostRequest {
  startSecond: number;
  length: number;
}
