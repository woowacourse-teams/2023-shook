import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CollectingInformation from '@/features/killingParts/components/CollectingInformation';
import RegisterPart from '@/features/killingParts/components/RegisterPart';
import SongInformation from '@/features/songs/components/SongInformation';
import { VoteInterfaceProvider } from '@/features/songs/components/VoteInterfaceProvider';
import VideoController from '@/features/youtube/components/VideoController';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex/Flex';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import type { VotingSongList } from '@/shared/types/song';

const PartCollectingPage = () => {
  const { id: songId } = useParams();
  // TODO: 조회 API 만들어야함.
  const { data: votingSongs } = useFetch<VotingSongList>(() =>
    fetcher(`/voting-songs/${songId}`, 'GET')
  );

  if (!votingSongs) return;
  const { id, title, singer, videoLength, songVideoId, albumCoverUrl } = votingSongs.currentSong;

  return (
    <VideoPlayerProvider>
      <VoteInterfaceProvider songVideoId={songVideoId} videoLength={videoLength} songId={id}>
        <Container>
          <FlexPage $gap={8} $direction="row" $md={{ $direction: 'column' }}>
            <FlexPlayer $gap={8} $direction="column">
              <SongInformation albumCoverUrl={albumCoverUrl} singer={singer} title={title} />
              <Youtube videoId={songVideoId} />
            </FlexPlayer>
            <FlexControlInterface $gap={8} $direction="column">
              <CollectingInformation />
              <VideoController />
              <RegisterPart />
            </FlexControlInterface>
          </FlexPage>
        </Container>
      </VoteInterfaceProvider>
    </VideoPlayerProvider>
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

const FlexControlInterface = styled(Flex)`
  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 320px;
    padding: 16px;
  }
`;

const FlexPage = styled(Flex)`
  padding: 10px;
  background-color: ${({ theme: { color } }) => color.black300};
  border-radius: 8px;
`;

const FlexPlayer = styled(Flex)`
  flex: 1;
`;
