import { client } from '@/shared/remotes/axios';
import type { LikeKillingPart } from '../components/MyPartList';

export const getLikeParts = async () => {
  const { data } = await client.get<LikeKillingPart[]>('/my-page/like-parts');

  return data;
};

export const getMyParts = async () => {
  const { data } = await client.get<LikeKillingPart[]>('/my-page/my-parts');

  return data;
};
