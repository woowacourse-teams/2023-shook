import { createContext, useCallback, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface VideoPlayerContextProps {
  videoPlayer: React.MutableRefObject<YT.Player | null>;
  playerState: YT.PlayerState | null;
  seekTo: (start: number) => void;
  pause: () => void;
  initPlayer: YT.PlayerEventHandler<YT.PlayerEvent>;
  updatePlayerState: YT.PlayerEventHandler<YT.PlayerEvent>;
}

export const VideoPlayerContext = createContext<VideoPlayerContextProps | null>(null);

export const VideoPlayerProvider = ({ children }: PropsWithChildren) => {
  const videoPlayer = useRef<YT.Player | null>(null);
  const [playerState, setPlayerState] = useState<YT.PlayerState | null>(null);

  const pause = useCallback(() => videoPlayer.current?.pauseVideo(), []);
  const play = useCallback(() => videoPlayer.current?.playVideo(), []);

  const seekTo = useCallback(
    (start: number) => {
      videoPlayer.current?.seekTo(start, true);
      play();
    },
    [play]
  );

  const initPlayer: YT.PlayerEventHandler<YT.PlayerEvent> = useCallback(({ target }) => {
    videoPlayer.current = target;
  }, []);

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
        initPlayer,
        updatePlayerState,
      }}
    >
      {children}
    </VideoPlayerContext.Provider>
  );
};
