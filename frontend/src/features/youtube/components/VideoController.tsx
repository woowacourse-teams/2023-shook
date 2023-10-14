import VideoIntervalStepper from '@/features/songs/components/VideoIntervalStepper';
import VideoBadges from '@/features/youtube/components/VideoBadges';
import WaveScrubber from '@/features/youtube/components/WaveScrubber';
import Spacing from '@/shared/components/Spacing';

const VideoController = () => {
  return (
    <div>
      <VideoIntervalStepper />
      <Spacing direction="vertical" size={8} />
      <VideoBadges />
      <Spacing direction="vertical" size={8} />
      <WaveScrubber />
    </div>
  );
};

export default VideoController;
