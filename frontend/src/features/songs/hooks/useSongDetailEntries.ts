import { useLayoutEffect, useRef } from 'react';
import useFetch from '@/shared/hooks/useFetch';
import useValidParams from '@/shared/hooks/useValidParams';
import { getSongDetailEntries } from '../remotes/songs';
import type { Genre } from '../types/Song.type';

const useSongDetailEntries = () => {
  const { id: songIdParams, genre: genreParams } = useValidParams();
  const currentSongDetailItemRef = useRef<HTMLDivElement>(null);

  const { data: songDetailEntries } = useFetch(() =>
    getSongDetailEntries(Number(songIdParams), genreParams as Genre)
  );

  useLayoutEffect(() => {
    currentSongDetailItemRef.current?.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, [songDetailEntries]);

  return { songDetailEntries, currentSongDetailItemRef };
};

export default useSongDetailEntries;
