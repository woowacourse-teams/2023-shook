import { createContext, useCallback, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface VideoPlayerContextProps {
  videoPlayer: YT.Player | null;
  updatePlayer: (player: YT.Player) => void;
}

export const VideoPlayerContext = createContext<VideoPlayerContextProps | null>(null);

export const VideoPlayerProvider = ({ children }: PropsWithChildren) => {
  const [videoPlayer, setVideoPlayer] = useState<YT.Player | null>(null);

  const updatePlayer = useCallback((player: YT.Player) => setVideoPlayer(player), []);

  return (
    <VideoPlayerContext.Provider value={{ videoPlayer, updatePlayer }}>
      {children}
    </VideoPlayerContext.Provider>
  );
};
