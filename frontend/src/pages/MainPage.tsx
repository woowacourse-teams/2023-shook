import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import CollectionCarousel from '@/features/songs/components/CollectionCarousel';
import PopularSongItem from '@/features/songs/components/PopularSongItem';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import ROUTE_PATH from '@/shared/constants/path';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';

interface PopularSong {
  id: number;
  title: string;
  singer: string;
  albumCoverUrl: string;
  totalVoteCount: number;
}

const MainPage = () => {
  const { data: popularSongs } = useFetch<PopularSong[]>(() => fetcher('/songs/high-voted', 'GET'));

  if (!popularSongs) return null;

  return (
    <>
      <SRHeading>shook 메인 페이지</SRHeading>

      <Title>현재 수집중인 노래</Title>
      <Spacing direction="vertical" size={12} />
      <CollectionCarousel />
      <Title>킬링파트 투표 많은순</Title>
      <Spacing direction="vertical" size={24} />
      <PopularSongList>
        {popularSongs.map(({ id, albumCoverUrl, title, singer, totalVoteCount }, i) => (
          <Li key={id}>
            <StyledLink
              to={`${ROUTE_PATH.SONG_DETAIL}/${id}`}
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
          </Li>
        ))}
      </PopularSongList>
    </>
  );
};

export default MainPage;

const Li = styled.li`
  width: 100%;
`;

const StyledLink = styled(Link)`
  width: 100%;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

const Title = styled.h2`
  align-self: flex-start;

  color: white;

  font-size: 20px;
  font-weight: 700;
`;

const PopularSongList = styled.ol`
  width: 100%;

  display: flex;
  flex-direction: column;
  align-items: flex-start;

  gap: 12px;
`;
