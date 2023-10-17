import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { getSingerDetail } from '@/features/search/remotes/search';
import Thumbnail from '@/features/songs/components/Thumbnail';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
import useFetch from '@/shared/hooks/useFetch';
import useValidParams from '@/shared/hooks/useValidParams';
import { toMinSecText } from '@/shared/utils/convertTime';

const SingerDetailPage = () => {
  const { singerId } = useValidParams();

  const { data: singerDetail } = useFetch(() => getSingerDetail(Number(singerId)));
  if (!singerDetail) return;

  const { id, profileImageUrl, singer, songs, totalSongCount } = singerDetail;

  return (
    <Container $direction="column">
      <Spacing direction="vertical" size={36} />
      <Title>아티스트</Title>
      <Spacing direction="vertical" size={18} />
      <SingerInfoContainer $align="center" $gap={24}>
        <ProfileImage src={profileImageUrl} alt={singer} />
        <Flex $direction="column" $gap={16}>
          <Name>{singer}</Name>
          <SongCount>{`등록된 노래 ${totalSongCount}개`}</SongCount>
        </Flex>
      </SingerInfoContainer>
      <Spacing direction="vertical" size={50} />
      <Title>곡</Title>
      <Spacing direction="vertical" size={18} />
      <SongsInfoContainer as="ol" $direction="column" $gap={12} $align="center">
        {songs.map(({ id, albumCoverUrl, title, videoLength }) => (
          <SongListItem as="li" key={id} $gap={16} $justify="center">
            <Thumbnail size="md" borderRadius={0} src={albumCoverUrl} />
            <FlexInfo $direction="column" $gap={8}>
              <SongTitle>{title}</SongTitle>
              <Singer>{singer}</Singer>
            </FlexInfo>
            <VideoLength $align="center" $justify="flex-end">
              {toMinSecText(videoLength)}
            </VideoLength>
          </SongListItem>
        ))}
      </SongsInfoContainer>
    </Container>
  );
};

export default SingerDetailPage;

const Title = styled.h2`
  font-size: 28px;
  font-weight: 700;
`;

const ProfileImage = styled.img`
  width: 100px;
  height: 100px;
  border-radius: 50%;
`;

const Name = styled.div`
  font-size: 36px;
  font-weight: 700;
`;

const SongCount = styled.p`
  font-size: 18px;
`;

const Container = styled(Flex)`
  width: 100%;
  height: 100vh;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};
  color: white;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const SingerInfoContainer = styled(Flex)`
  width: 370px;
  height: 240px;
  padding: 20px;

  background-color: ${({ theme: { color } }) => color.black500};
  border-radius: 8px;

  &:hover {
    background-color: ${({ theme: { color } }) => color.secondary};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 100%;
  }
`;

const SongListItem = styled(Flex)`
  width: 100%;
  padding: 8px;
  border-radius: 4px;

  &:hover {
    background-color: ${({ theme: { color } }) => color.secondary};
  }
`;

const SongsInfoContainer = styled(Flex)`
  width: 100%;
  border-radius: 8px;
`;

const FlexInfo = styled(Flex)`
  width: 80%;
`;

const SongTitle = styled.div`
  overflow: hidden;

  font-size: 16px;
  font-weight: 700;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Singer = styled.div`
  overflow: hidden;
  font-size: 14px;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

const VideoLength = styled(Flex)`
  flex: 1 0 0;
  font-size: 16px;
`;
