import fetcher from '@/shared/remotes';
import type { ArtistSearchPreview, ArtistSearchResult } from '../types/search';

export const getArtistSearchPreview = async (query: string): Promise<ArtistSearchPreview[]> => {
  const encodedQuery = encodeURIComponent(query);
  return await fetcher(`/singers?name=${encodedQuery}&search=singer`, 'GET');
};

export const getArtistSearch = async (query: string): Promise<ArtistSearchResult[]> => {
  const encodedQuery = encodeURIComponent(query);
  return await fetcher(`/singers?name=${encodedQuery}&search=singer,song`, 'GET');
};
