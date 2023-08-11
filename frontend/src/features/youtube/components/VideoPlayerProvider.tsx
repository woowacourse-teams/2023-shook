import { createContext, useCallback, useRef, useState } from 'react';
import type { PropsWithChildren } from 'react';

interface VideoPlayerContextProps {
  videoPlayer: React.MutableRefObject<YT.Player | undefined>;
}

export const VideoPlayerContext = createContext<VideoPlayerContextProps | null>(null);

export const VideoPlayerProvider = ({ children }: PropsWithChildren) => {
  const videoPlayer = useRef<YT.Player>();

  return (
    <VideoPlayerContext.Provider value={{ videoPlayer }}>{children}</VideoPlayerContext.Provider>
  );
};
