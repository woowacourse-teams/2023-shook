import { useCallback, useRef } from 'react';
import useExtraFetch from '@/shared/hooks/useExtraFetch';
import useValidParams from '@/shared/hooks/useValidParams';
import createObserver from '@/shared/utils/createObserver';
import { getExtraNextSongDetails, getExtraPrevSongDetails } from '../remotes/songs';
import type { Genre } from '../types/Song.type';

const useExtraSongDetail = () => {
  const { genre: genreParams } = useValidParams();

  const { data: extraPrevSongDetails, fetchData: fetchExtraPrevSongDetails } = useExtraFetch(
    getExtraPrevSongDetails,
    'prev'
  );

  const { data: extraNextSongDetails, fetchData: fetchExtraNextSongDetails } = useExtraFetch(
    getExtraNextSongDetails,
    'next'
  );

  const prevObserverRef = useRef<IntersectionObserver | null>(null);
  const nextObserverRef = useRef<IntersectionObserver | null>(null);

  const getExtraPrevSongDetailsOnObserve: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom !== null) {
      prevObserverRef.current = createObserver(() =>
        fetchExtraPrevSongDetails(getFirstSongId(dom), genreParams as Genre)
      );

      prevObserverRef.current.observe(dom);
      return;
    }

    prevObserverRef.current?.disconnect();
  }, []);

  const getExtraNextSongDetailsOnObserve: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom !== null) {
      nextObserverRef.current = createObserver(() =>
        fetchExtraNextSongDetails(getLastSongId(dom), genreParams as Genre)
      );

      nextObserverRef.current.observe(dom);
      return;
    }

    nextObserverRef.current?.disconnect();
  }, []);

  const getFirstSongId = (dom: HTMLDivElement) => {
    const firstSongId = dom.nextElementSibling?.getAttribute('data-song-id') as string;

    return Number(firstSongId);
  };

  const getLastSongId = (dom: HTMLDivElement) => {
    const lastSongId = dom.previousElementSibling?.getAttribute('data-song-id') as string;

    return Number(lastSongId);
  };

  return {
    extraPrevSongDetails,
    extraNextSongDetails,
    getExtraPrevSongDetailsOnObserve,
    getExtraNextSongDetailsOnObserve,
  };
};

export default useExtraSongDetail;
