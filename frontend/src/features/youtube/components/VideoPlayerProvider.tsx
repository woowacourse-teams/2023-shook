import { createContext, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface VideoPlayerContextProps {
  videoPlayer: React.MutableRefObject<YT.Player | undefined>;
  playerState: YT.PlayerState | undefined;
  seekTo: (start: number) => void;
  pause: () => void;
  updatePlayerState: YT.PlayerEventHandler<YT.PlayerEvent>;
}

export const VideoPlayerContext = createContext<VideoPlayerContextProps | null>(null);

export const VideoPlayerProvider = ({ children }: PropsWithChildren) => {
  const videoPlayer = useRef<YT.Player>();
  const [playerState, setPlayerState] = useState<YT.PlayerState>();

  const pause = () => videoPlayer.current?.pauseVideo();
  const play = () => videoPlayer.current?.playVideo();

  const seekTo = (start: number) => {
    videoPlayer.current?.seekTo(start, true);
    play();
  };

  const updatePlayerState: YT.PlayerEventHandler<YT.PlayerEvent> = ({ target }) => {
    const state = target.getPlayerState();

    setPlayerState(state);
  };

  return (
    <VideoPlayerContext.Provider
      value={{
        videoPlayer,
        playerState,
        seekTo,
        pause,
        updatePlayerState,
      }}
    >
      {children}
    </VideoPlayerContext.Provider>
  );
};
