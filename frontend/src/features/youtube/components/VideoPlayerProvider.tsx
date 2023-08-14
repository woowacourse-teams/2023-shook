import { createContext, useCallback, useRef, useState } from 'react';
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

  const pause = useCallback(() => videoPlayer.current?.pauseVideo(), []);
  const play = useCallback(() => videoPlayer.current?.playVideo(), []);

  const seekTo = useCallback(
    (start: number) => {
      videoPlayer.current?.seekTo(start, true);
      play();
    },
    [play]
  );

  const updatePlayerState: YT.PlayerEventHandler<YT.PlayerEvent> = useCallback(({ target }) => {
    const state = target.getPlayerState();

    setPlayerState(state);
  }, []);

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
