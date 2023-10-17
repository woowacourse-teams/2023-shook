import styled from 'styled-components';
import VideoBadges from '@/features/killingParts/components/VideoBadges';
import VideoIntervalStepper from '@/features/killingParts/components/VideoIntervalStepper';
import WaveScrubber from '@/features/killingParts/components/WaveScrubber';
import Flex from '@/shared/components/Flex/Flex';

const VideoController = () => {
  return (
    <Flex $gap={8} $direction={'column'}>
      <SubHeading>길이 선택</SubHeading>
      <VideoIntervalStepper />

      <SubHeading>구간 지정</SubHeading>
      <VideoBadges />
      <WaveScrubber />
    </Flex>
  );
};

export default VideoController;

const SubHeading = styled.div`
  display: none;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    display: unset;
    font-size: 20px;
    margin-top: 8px;
  }
`;
