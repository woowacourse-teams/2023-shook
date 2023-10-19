import { styled } from 'styled-components';
import CarouselItem from '@/features/songs/components/CarouselItem';
import CollectionCarousel from '@/features/songs/components/CollectionCarousel';
import SongItemList from '@/features/songs/components/SongItemList';
import GENRES from '@/features/songs/constants/genres';
import { getRecentSongs } from '@/features/songs/remotes/song';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import useFetch from '@/shared/hooks/useFetch';
import type { Genre } from '@/features/songs/types/Song.type';

const genres = Object.keys(GENRES) as Genre[];

const MainPage = () => {
  const { data: recentSongs } = useFetch(() => getRecentSongs());

  if (!recentSongs) return null;

  return (
    <Container>
      <SRHeading>shook 메인 페이지</SRHeading>
      <Title>현재 킬링파트 등록중인 노래</Title>
      <Spacing direction="vertical" size={16} />
      <CollectionCarousel>
        {recentSongs.map((recentSong) => (
          <CarouselItem key={recentSong.id} recentSong={recentSong} />
        ))}
      </CollectionCarousel>
      <Spacing direction="vertical" size={24} />
      {genres.map((genre) => (
        <SongItemList key={genre} genre={genre} />
      ))}
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

const Title = styled.h2`
  align-self: flex-start;
  font-size: 20px;
  font-weight: 700;
  color: white;
`;
