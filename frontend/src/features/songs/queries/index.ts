import { queryOptions, useInfiniteQuery } from '@tanstack/react-query';
import {
  getExtraNextSongDetails,
  getExtraPrevSongDetails,
  getSongDetailEntries,
} from '../remotes/songs';
import type { Genre } from '../types/Song.type';

export const songDetailEntriesQueryOptions = (songId: number, genre: Genre) =>
  queryOptions({
    queryKey: ['songDetailEntries', songId, genre],
    queryFn: () => getSongDetailEntries(songId, genre),
    staleTime: Infinity,
  });

export const useExtraPrevSongDetailsInfiniteQuery = (songId: number, genre: Genre) => {
  const {
    data: extraPrevSongDetails,
    fetchPreviousPage: fetchExtraPrevSongDetails,
    ...infiniteQueries
  } = useInfiniteQuery({
    queryKey: ['extraPrevSongDetails'],
    queryFn: ({ pageParam }) => getExtraPrevSongDetails(pageParam, genre),
    getPreviousPageParam: (firstPage) => firstPage[0]?.id ?? null,
    getNextPageParam: () => null,
    initialPageParam: songId,
    staleTime: Infinity,
  });

  return { extraPrevSongDetails, fetchExtraPrevSongDetails, infiniteQueries };
};

export const useExtraNextSongDetailsInfiniteQuery = (songId: number, genre: Genre) => {
  const {
    data: extraNextSongDetails,
    fetchNextPage: fetchExtraNextSongDetails,
    ...infiniteQueries
  } = useInfiniteQuery({
    queryKey: ['extraNextSongDetails'],
    queryFn: ({ pageParam }) => getExtraNextSongDetails(pageParam, genre),
    getNextPageParam: (lastPage) => lastPage.at(-1)?.id ?? null,
    initialPageParam: songId,
    staleTime: Infinity,
  });

  return { extraNextSongDetails, fetchExtraNextSongDetails, infiniteQueries };
};
