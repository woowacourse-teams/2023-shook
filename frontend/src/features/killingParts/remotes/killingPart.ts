import client from '@/shared/remotes/axios';
import type { SongInfo } from '@/shared/types/song';

// PartCollectingPage에 존재하던 remote 함수입니다.
// useFetch<SongInfo>(() => fetcher(`/songs/${songId}`, 'GET')) 로직에서 분리하였습니다.
// SongInfo type에는 killingPart[] 필드가 있는데, 마이파트 수집용 `노래 1개` 조회에서 해당 타입이 사용되고 있습니다.
// 추후 수정되어야 합니다.

export const getSong = async (songId: number) => {
  const { data } = await client.get<SongInfo>(`/songs/${songId}`);

  return data;
};
