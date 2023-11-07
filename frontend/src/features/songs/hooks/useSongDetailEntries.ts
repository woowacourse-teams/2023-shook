import { useCallback } from 'react';
import useFetch from '@/shared/hooks/useFetch';
import useValidParams from '@/shared/hooks/useValidParams';
import { getSongDetailEntries } from '../remotes/songs';
import type { Genre } from '../types/Song.type';

const useSongDetailEntries = () => {
  const { id: songIdParams, genre: genreParams } = useValidParams();

  const { data: songDetailEntries } = useFetch(() =>
    getSongDetailEntries(Number(songIdParams), genreParams as Genre)
  );

  const scrollIntoCurrentSong: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom !== null) dom.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, []);

  return { songDetailEntries, scrollIntoCurrentSong };
};

export default useSongDetailEntries;
