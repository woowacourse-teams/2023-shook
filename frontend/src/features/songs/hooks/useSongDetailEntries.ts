import { useCallback } from 'react';
import useValidParams from '@/shared/hooks/useValidParams';
import { useSongDetailEntriesQuery } from '../queries';
import type { Genre } from '../types/Song.type';

const useSongDetailEntries = () => {
  const { id: songIdParams, genre: genreParams } = useValidParams();

  const {
    songDetailEntries,
    queries: { isLoading: isLoadingSongDetailEntries },
  } = useSongDetailEntriesQuery(Number(songIdParams), genreParams as Genre);

  const scrollIntoCurrentSong: React.RefCallback<HTMLDivElement> = useCallback((dom) => {
    if (dom !== null) dom.scrollIntoView({ behavior: 'instant', block: 'start' });
  }, []);

  return { songDetailEntries, isLoadingSongDetailEntries, scrollIntoCurrentSong };
};

export default useSongDetailEntries;
