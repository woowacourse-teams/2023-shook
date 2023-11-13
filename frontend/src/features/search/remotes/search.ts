import client from '@/shared/remotes/axios';
import type { SingerDetail } from '../../singer/types/singer.type';
import type { SingerSearchPreview } from '../types/search.type';

export const getSingerSearchPreview = async (query: string) => {
  const encodedQuery = encodeURIComponent(query);
  const { data } = await client.get<SingerSearchPreview[]>(
    `/search?keyword=${encodedQuery}&type=singer`
  );

  return data;
};

export const getSingerSearch = async (query: string) => {
  const encodedQuery = encodeURIComponent(query);
  const { data } = await client.get<SingerDetail[]>(
    `/search?keyword=${encodedQuery}&type=singer&type=song`
  );

  return data;
};
