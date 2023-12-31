import { Flex } from 'shook-layout';
import { styled } from 'styled-components';
import RegisterPart from '@/features/killingParts/components/RegisterPart';
import VideoController from '@/features/killingParts/components/VideoController';
import { getSong } from '@/features/killingParts/remotes/killingPart';
import { CollectingPartProvider } from '@/features/songs/components/CollectingPartProvider';
import SongInformation from '@/features/songs/components/SongInformation';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import useFetch from '@/shared/hooks/useFetch';
import useValidParams from '@/shared/hooks/useValidParams';

const PartCollectingPage = () => {
  const { id: songId } = useValidParams();
  // TODO: 조회 API 만들어야함.
  const { data: songInfo } = useFetch(() => getSong(Number(songId)));

  if (!songInfo) return;
  const { id, title, singer, videoLength, songVideoId, albumCoverUrl } = songInfo;

  return (
    <>
      <SRHeading>파트 등록 페이지</SRHeading>
      <HeaderSpacing direction={'vertical'} size={50} />
      <VideoPlayerProvider>
        <CollectingPartProvider songVideoId={songVideoId} videoLength={videoLength} songId={id}>
          <PageFlex $gap={4} $direction="row" $md={{ $direction: 'column' }}>
            <SongPlayerFlex $gap={10} $direction="column">
              <SongInformation albumCoverUrl={albumCoverUrl} singer={singer} title={title} />
              <Youtube videoId={songVideoId} controls={0} />
            </SongPlayerFlex>
            <ControllerFlex $gap={10} $direction="column">
              <RegisterTitle>나만의 파트 저장하기</RegisterTitle>
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
  padding: 8px;

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

const RegisterTitle = styled.h2`
  font-size: 20px;
  font-weight: 800;
  color: ${({ theme: { color } }) => color.white};

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 24px;
  }
`;
