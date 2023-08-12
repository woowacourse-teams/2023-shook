import { createContext, useRef } from 'react';
import type { PropsWithChildren } from 'react';

interface VideoPlayerContextProps {
  videoPlayer: React.MutableRefObject<YT.Player | undefined>;
  play: (start: number) => void;
  togglePlayerPauseAndResume: () => void;
}

export const VideoPlayerContext = createContext<VideoPlayerContextProps | null>(null);

export const VideoPlayerProvider = ({ children }: PropsWithChildren) => {
  const videoPlayer = useRef<YT.Player>();

  const pause = () => videoPlayer.current?.pauseVideo();
  const resume = () => videoPlayer.current?.playVideo();

  const play = (start: number) => {
    const isPause = videoPlayer.current?.getPlayerState() === 2;
    if (isPause) {
      resume();
    }

    videoPlayer.current?.seekTo(start, true);
  };

  const togglePlayerPauseAndResume = () => {
    const isPlaying = videoPlayer.current?.getPlayerState() === 1;
    const isPaused = videoPlayer.current?.getPlayerState() === 2;

    if (isPlaying) {
      pause();
      return;
    }

    if (isPaused) {
      resume();
      return;
    }
  };

  return (
    <VideoPlayerContext.Provider value={{ videoPlayer, play, togglePlayerPauseAndResume }}>
      {children}
    </VideoPlayerContext.Provider>
  );
};
