import { client } from '@/shared/remotes/axios';
import type { SingerDetail } from '../../singer/types/singer.type';
import type { SingerSearchPreview } from '../types/search.type';

export const getSingerSearchPreview = async (query: string) => {
  const { data } = await client.get<SingerSearchPreview[]>(`/search`, {
    params: {
      keyword: query,
      type: 'singer',
    },
  });

  return data;
};

export const getSingerSearch = async (query: string) => {
  const params = new URLSearchParams();
  params.append('keyword', query);
  params.append('type', 'singer');
  params.append('type', 'song');

  const { data } = await client.get<SingerDetail[]>(`/search`, { params });

  return data;
};
