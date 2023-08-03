import fetcher from '@/apis';
import PopularSongItem from '@/components/PopularSongItem';
import useFetch from '@/hooks/@common/useFetch';
import { Spacing } from '../SongDetailPage.style';
import { PopularSongList, StyledLink, Title } from './SongPopularPage.style';

interface PopularSong {
  id: number;
  thumbnail: string;
  title: string;
  singer: string;
}

const SongPopularPage = () => {
  const { data: popularSongs } = useFetch<PopularSong[]>(() => fetcher('/songs/high-voted', 'GET'));

  if (!popularSongs) return null;

  return (
    <>
      <Title>킬링파트 등록 인기순</Title>
      <Spacing direction="vertical" size={24} />
      <PopularSongList>
        {popularSongs.map(({ id, thumbnail, title, singer }, i) => (
          <StyledLink
            key={id}
            to={`/song/${id}`}
            aria-label={`킬링파트 인기순 ${i + 1}등 ${singer} ${title}`}
          >
            <PopularSongItem rank={i + 1} thumbnail={thumbnail} title={title} singer={singer} />
          </StyledLink>
        ))}
      </PopularSongList>
    </>
  );
};

export default SongPopularPage;