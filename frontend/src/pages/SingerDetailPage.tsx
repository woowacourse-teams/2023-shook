import styled from 'styled-components';
import { getSingerDetail } from '@/features/search/remotes/singer';
import SingerBanner from '@/features/singer/components/SingerBanner';
import SingerSongItem from '@/features/singer/components/SingerSongItem';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
import useFetch from '@/shared/hooks/useFetch';
import useValidParams from '@/shared/hooks/useValidParams';

const SingerDetailPage = () => {
  const { singerId } = useValidParams();

  const { data: singerDetail } = useFetch(() => getSingerDetail(Number(singerId)));
  if (!singerDetail) return;

  const { profileImageUrl, singer, songs, totalSongCount } = singerDetail;

  return (
    <Container $direction="column">
      <Spacing direction="vertical" size={36} />
      <Title>아티스트</Title>
      <Spacing direction="vertical" size={18} />
      <SingerBanner
        profileImageUrl={profileImageUrl}
        singer={singer}
        totalSongCount={totalSongCount}
      />
      <Spacing direction="vertical" size={68} $md={{ size: 34 }} />
      <Title>곡</Title>
      <Spacing direction="vertical" size={18} />
      <SongsItemList as="ol" $direction="column" $gap={12} $align="center">
        {songs.map((song) => (
          <SingerSongItem key={song.id} {...song} />
        ))}
      </SongsItemList>
    </Container>
  );
};

export default SingerDetailPage;

const Container = styled(Flex)`
  width: 100%;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const SongsItemList = styled(Flex)`
  overflow-y: scroll;
  width: 100%;
`;

const Title = styled.h2`
  font-size: 28px;
  font-weight: 700;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    font-size: 24px;
  }
`;
