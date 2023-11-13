import { client } from '@/shared/remotes/axios';
import type { SingerDetail } from '../types/singer.type';

export const getSingerDetail = async (singerId: number) => {
  const { data } = await client.get<SingerDetail>(`/singers/${singerId}`);

  return data;
};
