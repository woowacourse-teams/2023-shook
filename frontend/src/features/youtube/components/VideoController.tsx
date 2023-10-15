import VideoIntervalStepper from '@/features/songs/components/VideoIntervalStepper';
import VideoBadges from '@/features/youtube/components/VideoBadges';
import WaveScrubber from '@/features/youtube/components/WaveScrubber';
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
