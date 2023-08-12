import { createContext, useRef } from 'react';
import type { PropsWithChildren } from 'react';

interface VideoPlayerContextProps {
  videoPlayer: React.MutableRefObject<YT.Player | undefined>;
  play: (start: number) => void;
  pause: () => void;
  currentPlayTime: number;
}

export const VideoPlayerContext = createContext<VideoPlayerContextProps | null>(null);

export const VideoPlayerProvider = ({ children }: PropsWithChildren) => {
  const videoPlayer = useRef<YT.Player>();

  const play = (start: number) => {
    if (videoPlayer.current?.getPlayerState() === 2) {
      videoPlayer.current?.playVideo();
    }

    videoPlayer.current?.seekTo(start, true);
  };

  const pause = () => {
    const isPlaying = videoPlayer.current?.getPlayerState() === 1;

    if (isPlaying) {
      videoPlayer.current?.pauseVideo();
    }
  };

  const currentPlayTime = videoPlayer.current?.getCurrentTime() ?? 0;

  return (
    <VideoPlayerContext.Provider value={{ videoPlayer, play, pause, currentPlayTime }}>
      {children}
    </VideoPlayerContext.Provider>
  );
};
