import useFetch from '@/shared/hooks/useFetch';
import { getSongDetail } from '../remotes/song';
import type { SongDetail } from '@/shared/types/song';

export const useGetSongDetail = (songId: number) => {
  const {
    data: songDetail,
    isLoading,
    error,
    fetchData,
  } = useFetch<SongDetail>(() => getSongDetail(songId));

  return { songDetail, isLoading, error, fetchData };
};
