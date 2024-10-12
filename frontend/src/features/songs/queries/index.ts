import { infiniteQueryOptions, queryOptions } from '@tanstack/react-query';
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

export const extraPrevSongDetailsInfiniteQueryOptions = (songId: number, genre: Genre) =>
  infiniteQueryOptions({
    queryKey: ['extraPrevSongDetails'],
    queryFn: ({ pageParam }) => getExtraPrevSongDetails(pageParam, genre),
    getPreviousPageParam: (firstPage) => firstPage[0]?.id ?? null,
    getNextPageParam: () => null,
    initialPageParam: songId,
    staleTime: Infinity,
  });

export const extraNextSongDetailsInfiniteQueryOptions = (songId: number, genre: Genre) =>
  infiniteQueryOptions({
    queryKey: ['extraNextSongDetails'],
    queryFn: ({ pageParam }) => getExtraNextSongDetails(pageParam, genre),
    getNextPageParam: (lastPage) => lastPage.at(-1)?.id ?? null,
    initialPageParam: songId,
    staleTime: Infinity,
  });
