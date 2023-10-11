import fetcher from '@/shared/remotes';
import type { SingerSearchPreview, SingerSearchResult } from '../types/search';

export const getSingerSearchPreview = async (query: string): Promise<SingerSearchPreview[]> => {
  const encodedQuery = encodeURIComponent(query);
  return await fetcher(`/singers?name=${encodedQuery}&search=singer`, 'GET');
};

export const getSingerSearch = async (query: string): Promise<SingerSearchResult[]> => {
  const encodedQuery = encodeURIComponent(query);
  return await fetcher(`/singers?name=${encodedQuery}&search=singer,song`, 'GET');
};

export const getSingerDetail = async (singerId: number): Promise<SingerSearchResult> => {
  return await fetcher(`/singers/${singerId}`, 'GET');
};
