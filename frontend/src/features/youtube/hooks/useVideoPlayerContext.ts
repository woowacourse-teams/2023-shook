import { useContext } from 'react';
import { VideoPlayerContext } from '../components/VideoPlayerProvider';

const useVideoPlayerContext = () => {
  const videoPlayerValues = useContext(VideoPlayerContext);
  if (!videoPlayerValues) throw new Error('VideoPlayerContext에 value가 제공되지 않았습니다.');

  return { ...videoPlayerValues };
};

export default useVideoPlayerContext;
