import fetcher from '@/shared/remotes';
import type { SingerDetail } from '../../singer/types/singer.type';

export const getSingerDetail = async (singerId: number): Promise<SingerDetail> => {
  return await fetcher(`/singers/${singerId}`, 'GET');
};
