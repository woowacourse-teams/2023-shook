import styled from 'styled-components';
import { getSingerDetail } from '@/features/search/remotes/singer';
import SingerBanner from '@/features/singer/components/SingerBanner';
import SingerSongList from '@/features/singer/components/SingerSongList';
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
      <Spacing direction="vertical" size={18} />
      <SingerBanner
        profileImageUrl={profileImageUrl}
        singer={singer}
        totalSongCount={totalSongCount}
      />
      <Spacing direction="vertical" size={68} $md={{ size: 34 }} />
      <SingerSongList songs={songs} />
      <Spacing direction="vertical" size={18} />
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
