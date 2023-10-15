import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CollectingInformation from '@/features/killingParts/components/CollectingInformation';
import RegisterPart from '@/features/killingParts/components/RegisterPart';
import { CollectingPartProvider } from '@/features/songs/components/CollectingPartProvider';
import SongInformation from '@/features/songs/components/SongInformation';
import VideoController from '@/features/youtube/components/VideoController';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex/Flex';
import SRHeading from '@/shared/components/SRHeading';
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
    <>
      <SRHeading>파트 등록 페이지</SRHeading>
      <VideoPlayerProvider>
        <CollectingPartProvider songVideoId={songVideoId} videoLength={videoLength} songId={id}>
          <PageFlex $gap={8} $direction="row" $md={{ $direction: 'column' }}>
            <FlexPlayer $gap={8} $direction="column">
              <SongInformation albumCoverUrl={albumCoverUrl} singer={singer} title={title} />
              <Youtube videoId={songVideoId} />
            </FlexPlayer>
            <FlexControlInterface $gap={8} $direction="column">
              <CollectingInformation />
              <VideoController />
              <RegisterPart />
            </FlexControlInterface>
          </PageFlex>
        </CollectingPartProvider>
      </VideoPlayerProvider>
    </>
  );
};

export default PartCollectingPage;

const PageFlex = styled(Flex)`
  padding: 10px;
  background-color: ${({ theme: { color } }) => color.black300};
  border-radius: 8px;

  width: 100%;
  margin: auto;
  transform: translateY(30px);

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 16px;
    transform: translateY(40px);
  }
`;

const FlexControlInterface = styled(Flex)`
  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 320px;
    padding: 16px;
  }
`;

const FlexPlayer = styled(Flex)`
  flex: 1;
  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    max-width: calc(100% - 320px);
  }
`;
