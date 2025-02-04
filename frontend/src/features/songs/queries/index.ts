import { infiniteQueryOptions, queryOptions } from '@tanstack/react-query';
import {
  getExtraNextSongDetails,
  getExtraPrevSongDetails,
  getSongDetailEntries,
} from '../remotes/songs';
import type { Genre } from '../types/Song.type';

export const songDetailQueries = {
  all: ['songDetail'],
  entries: ({ songId, genre }: { songId: number; genre: Genre }) =>
    queryOptions({
      queryKey: [...songDetailQueries.all, 'entries', songId, genre],
      queryFn: () => getSongDetailEntries(songId, genre),
      staleTime: Infinity,
    }),
  prevEntries: ({ songId, genre }: { songId: number; genre: Genre }) =>
    infiniteQueryOptions({
      queryKey: [...songDetailQueries.all, 'prevEntries', songId, genre],
      queryFn: ({ pageParam }) => getExtraPrevSongDetails(pageParam, genre),
      getPreviousPageParam: (firstPage) => firstPage[0]?.id ?? null,
      getNextPageParam: () => null,
      initialPageParam: songId,
      staleTime: Infinity,
    }),
  nextEntries: ({ songId, genre }: { songId: number; genre: Genre }) =>
    infiniteQueryOptions({
      queryKey: [...songDetailQueries.all, 'nextEntries', songId, genre],
      queryFn: ({ pageParam }) => getExtraNextSongDetails(pageParam, genre),
      getNextPageParam: (lastPage) => lastPage.at(-1)?.id ?? null,
      initialPageParam: songId,
      staleTime: Infinity,
    }),
};
