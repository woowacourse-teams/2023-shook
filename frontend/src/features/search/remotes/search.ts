import fetcher from '@/shared/remotes';
import type { SingerDetail } from '../../singer/types/singer.type';
import type { SingerSearchPreview } from '../types/search.type';

export const getSingerSearchPreview = async (query: string): Promise<SingerSearchPreview[]> => {
  const encodedQuery = encodeURIComponent(query);
  return await fetcher(`/search?keyword=${encodedQuery}&type=singer`, 'GET');
};

export const getSingerSearch = async (query: string): Promise<SingerDetail[]> => {
  const encodedQuery = encodeURIComponent(query);
  return await fetcher(`/search?keyword=${encodedQuery}&type=singer&type=song`, 'GET');
};
