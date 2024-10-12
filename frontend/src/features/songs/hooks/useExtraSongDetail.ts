import { useInfiniteQuery } from '@tanstack/react-query';
import { useCallback, useRef } from 'react';
import useValidParams from '@/shared/hooks/useValidParams';
import createObserver from '@/shared/utils/createObserver';
import {
  extraPrevSongDetailsInfiniteQueryOptions,
  extraNextSongDetailsInfiniteQueryOptions,
} from '../queries';
import type { Genre } from '../types/Song.type';

const useExtraSongDetail = () => {
  const { id: songIdParams, genre: genreParams } = useValidParams();

  const {
    data: extraPrevSongDetails,
    fetchPreviousPage: fetchExtraPrevSongDetails,
    isLoading: isLoadingPrevSongDetails,
    hasPreviousPage,
  } = useInfiniteQuery(
    extraPrevSongDetailsInfiniteQueryOptions(Number(songIdParams), genreParams as Genre)
  );

  const {
    data: extraNextSongDetails,
    fetchPreviousPage: fetchExtraNextSongDetails,
    isLoading: isLoadingNextSongDetails,
    hasNextPage,
  } = useInfiniteQuery(
    extraNextSongDetailsInfiniteQueryOptions(Number(songIdParams), genreParams as Genre)
  );

  const prevObserverRef = useRef<IntersectionObserver | null>(null);
  const nextObserverRef = useRef<IntersectionObserver | null>(null);

  const getExtraPrevSongDetailsOnObserve: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom !== null) {
      prevObserverRef.current = createObserver(() => fetchExtraPrevSongDetails());

      prevObserverRef.current.observe(dom);
      return;
    }

    prevObserverRef.current?.disconnect();
  }, []);

  const getExtraNextSongDetailsOnObserve: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom !== null) {
      nextObserverRef.current = createObserver(() => fetchExtraNextSongDetails());

      nextObserverRef.current.observe(dom);
      return;
    }

    nextObserverRef.current?.disconnect();
  }, []);

  return {
    extraPrevSongDetails,
    extraNextSongDetails,
    isLoadingPrevSongDetails,
    isLoadingNextSongDetails,
    hasPreviousPage,
    hasNextPage,
    getExtraPrevSongDetailsOnObserve,
    getExtraNextSongDetailsOnObserve,
  };
};

export default useExtraSongDetail;
