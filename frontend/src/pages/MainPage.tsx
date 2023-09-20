import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import CarouselItem from '@/features/songs/components/CarouselItem';
import CollectionCarousel from '@/features/songs/components/CollectionCarousel';
import SongItem from '@/features/songs/components/SongItem';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import ROUTE_PATH from '@/shared/constants/path';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import type { PopularSong, VotingSong } from '@/features/songs/types/Song.type';

const MainPage = () => {
  const { data: popularSongs } = useFetch<PopularSong[]>(() => fetcher('/songs/high-liked', 'GET'));
  const { data: votingSongs } = useFetch<VotingSong[]>(() => fetcher('/voting-songs', 'GET'));

  if (!popularSongs || !votingSongs) return null;

  const isEmptyVotingSongs = votingSongs.length === 0;

  return (
    <Container>
      <SRHeading>shook 메인 페이지</SRHeading>
      <Title>현재 킬링파트 등록중인 노래</Title>
      <Spacing direction="vertical" size={16} />
      <CollectionCarousel>
        {isEmptyVotingSongs ? (
          <EmptyMessage>
            <span>수집중인 노래가 곧 등록될 예정입니다.</span>
          </EmptyMessage>
        ) : (
          votingSongs.map((votingSong) => {
            return <CarouselItem key={votingSong.id} votingSong={votingSong} />;
          })
        )}
      </CollectionCarousel>
      <Spacing direction="vertical" size={24} />
      <Title>킬링파트 좋아요 많은순</Title>
      <Spacing direction="vertical" size={16} />
      <PopularSongList>
        {popularSongs.map(({ id, albumCoverUrl, title, singer, totalLikeCount }, i) => (
          <Li key={id}>
            <StyledLink
              to={`${ROUTE_PATH.SONG_DETAILS}/${id}`}
              aria-label={`킬링파트 투표순 ${i + 1}등 ${singer} ${title}`}
            >
              <SongItem
                rank={i + 1}
                albumCoverUrl={albumCoverUrl}
                title={title}
                singer={singer}
                totalLikeCount={totalLikeCount}
              />
            </StyledLink>
          </Li>
        ))}
      </PopularSongList>
    </Container>
  );
};

export default MainPage;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const Li = styled.li`
  max-width: 250px;
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
  font-size: 20px;
  font-weight: 700;
  color: white;
`;

const PopularSongList = styled.ol`
  overflow-x: scroll;
  display: flex;
  flex-direction: row;
  gap: 8px;
  align-items: flex-start;

  width: 100%;
`;

const EmptyMessage = styled.li`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 100%;
  min-width: 350px;
`;
