import VideoBadges from '@/features/killingParts/components/VideoBadges';
import VideoIntervalStepper from '@/features/killingParts/components/VideoIntervalStepper';
import WaveScrubber from '@/features/killingParts/components/WaveScrubber';
import Flex from '@/shared/components/Flex/Flex';

const VideoController = () => {
  return (
    <Flex $gap={8} $direction={'column'}>
      <VideoIntervalStepper />
      <VideoBadges />
      <WaveScrubber />
    </Flex>
  );
};

export default VideoController;
