import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import Thumbnail from '@/features/songs/components/Thumbnail';
import VoteInterface from '@/features/songs/components/VoteInterface';
import { VoteInterfaceProvider } from '@/features/songs/components/VoteInterfaceProvider';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import type { VotingSongList } from '@/shared/types/song';

const PartCollectingPage = () => {
  const { id: songId } = useParams();
  const { data: votingSongs } = useFetch<VotingSongList>(() =>
    fetcher(`/voting-songs/${songId}`, 'GET')
  );

  if (!votingSongs) return;
  const { id, title, singer, videoLength, songVideoId, albumCoverUrl } = votingSongs.currentSong;

  return (
    <Container>
      <VideoPlayerProvider>
        <FlexWrapper $direction="row" $md={{ $direction: 'column' }}>
          <Flex $direction="column" $css={{ flex: '1' }}>
            <SongInfoContainer>
              <Thumbnail size="sm" src={albumCoverUrl} alt={`${title} 앨범 자켓`} />
              <Info>
                <SongTitle>{title}</SongTitle>
                <Singer>{singer}</Singer>
              </Info>
            </SongInfoContainer>
            <Spacing direction="vertical" size={8} />
            <Youtube videoId={songVideoId} />
            <Spacing direction="vertical" size={8} />
          </Flex>
          <VoteInterfaceProvider songVideoId={songVideoId} videoLength={videoLength} songId={id}>
            <VoteInterface />
          </VoteInterfaceProvider>
        </FlexWrapper>
      </VideoPlayerProvider>
    </Container>
  );
};

export default PartCollectingPage;

const Container = styled.section`
  display: flex;
  flex: 0.6;
  flex-direction: column;
  justify-content: center;

  width: 100%;
  margin-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    margin-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    margin-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const SongInfoContainer = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;

const Info = styled.div`
  overflow: hidden;
  flex: 1;
`;

const SongTitle = styled.p`
  overflow: hidden;

  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};
  text-overflow: ellipsis;
  white-space: nowrap;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;

const Singer = styled.p`
  overflow: hidden;

  font-size: 16px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};
  text-overflow: ellipsis;
  white-space: nowrap;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 14px;
  }
`;

const FlexWrapper = styled(Flex)`
  padding: 10px;
  background-color: ${({ theme: { color } }) => color.black300};
  border-radius: 8px;
`;
