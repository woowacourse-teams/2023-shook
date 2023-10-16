import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CollectingInformation from '@/features/killingParts/components/CollectingInformation';
import RegisterPart from '@/features/killingParts/components/RegisterPart';
import VideoController from '@/features/killingParts/components/VideoController';
import { CollectingPartProvider } from '@/features/songs/components/CollectingPartProvider';
import SongInformation from '@/features/songs/components/SongInformation';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
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
      <HeaderSpacing direction={'vertical'} size={50} />
      <VideoPlayerProvider>
        <CollectingPartProvider songVideoId={songVideoId} videoLength={videoLength} songId={id}>
          <PageFlex $gap={8} $direction="row" $md={{ $direction: 'column' }}>
            <SongPlayerFlex $gap={8} $direction="column">
              <SongInformation albumCoverUrl={albumCoverUrl} singer={singer} title={title} />
              <Youtube videoId={songVideoId} />
            </SongPlayerFlex>
            <ControllerFlex $gap={8} $direction="column">
              <CollectingInformation />
              <VideoController />
              <RegisterPart />
            </ControllerFlex>
          </PageFlex>
        </CollectingPartProvider>
      </VideoPlayerProvider>
    </>
  );
};

export default PartCollectingPage;

const HeaderSpacing = styled(Spacing)`
  @media (min-width: ${({ theme }) => theme.breakPoints.xs}) {
    min-height: 80px;
  }
`;

const PageFlex = styled(Flex)`
  width: 100%;
  margin: auto;
  padding: 10px;

  background-color: ${({ theme: { color } }) => color.black300};
  border-radius: 8px;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    gap: 16px;
    padding: 16px;
  }
`;

const ControllerFlex = styled(Flex)`
  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 320px;
  }
`;

const SongPlayerFlex = styled(Flex)`
  flex: 1;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    max-width: calc(100% - 320px);
  }
`;
