import fetcher from '@/apis';
import { Spacing } from '@/components/@common';
import SRHeading from '@/components/@common/SRHeading';
import PopularSongItem from '@/components/PopularSongItem';
import useFetch from '@/hooks/@common/useFetch';
import { PopularSongList, StyledLink, Title } from './SongPopularPage.style';

interface PopularSong {
  id: number;
  title: string;
  singer: string;
  albumCoverUrl: string;
  totalVoteCount: number;
}

const SongPopularPage = () => {
  const { data: popularSongs } = useFetch<PopularSong[]>(() => fetcher('/songs/high-voted', 'GET'));

  if (!popularSongs) return null;

  return (
    <>
      <SRHeading>shook 메인 페이지</SRHeading>
      <Title>킬링파트 투표 많은순</Title>
      <Spacing direction="vertical" size={24} />
      <PopularSongList>
        {popularSongs.map(({ id, albumCoverUrl, title, singer, totalVoteCount }, i) => (
          <StyledLink
            key={id}
            to={`/song/${id}`}
            aria-label={`킬링파트 투표순 ${i + 1}등 ${singer} ${title}`}
          >
            <PopularSongItem
              rank={i + 1}
              albumCoverUrl={albumCoverUrl}
              title={title}
              singer={singer}
              totalVoteCount={totalVoteCount}
            />
          </StyledLink>
        ))}
      </PopularSongList>
    </>
  );
};

export default SongPopularPage;
