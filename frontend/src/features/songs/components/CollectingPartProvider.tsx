import { createContext, useEffect, useState } from 'react';
import { MAX_PART_INTERVAL, MIN_PART_INTERVAL } from '@/features/songs/constants/partInterval';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import type { PropsWithChildren } from 'react';

interface CollectingPartProviderProps {
  videoLength: number;
  songId: number;
  songVideoId: string;
}

interface CollectingPartContextProps extends CollectingPartProviderProps {
  partStartTime: number;
  interval: number;
  isPlayingEntire: boolean;
  setPartStartTime: React.Dispatch<React.SetStateAction<number>>;
  plusPartInterval: () => void;
  minusPartInterval: () => void;
  toggleEntirePlaying: () => void;
}

export const CollectingPartContext = createContext<CollectingPartContextProps | null>(null);
export const CollectingPartProvider = ({
  children,
  videoLength,
  songId,
  songVideoId,
}: PropsWithChildren<CollectingPartProviderProps>) => {
  const [interval, setInterval] = useState(10);
  const [partStartTime, setPartStartTime] = useState(0);
  const [isPlayingEntire, setIsPlayingEntire] = useState(false);
  const { playerState, seekTo } = useVideoPlayerContext();

  const toggleEntirePlaying = () => {
    setIsPlayingEntire((prev) => !prev);
  };

  const plusPartInterval = () => {
    setInterval((prevInterval) => {
      const currentInterval = prevInterval + 1;
      if (currentInterval >= MIN_PART_INTERVAL && currentInterval <= MAX_PART_INTERVAL) {
        return currentInterval;
      }
      return prevInterval;
    });
  };

  const minusPartInterval = () => {
    setInterval((prevInterval) => {
      const currentInterval = prevInterval - 1;
      if (currentInterval >= MIN_PART_INTERVAL && currentInterval <= MAX_PART_INTERVAL) {
        return currentInterval;
      }
      return prevInterval;
    });
  };

  useEffect(() => {
    if (isPlayingEntire || playerState === YT.PlayerState.PAUSED) return;

    const timer = window.setInterval(() => {
      seekTo(partStartTime);
    }, interval * 1000);

    return () => {
      window.clearInterval(timer);
    };
  }, [playerState, partStartTime, interval, isPlayingEntire]);

  return (
    <CollectingPartContext.Provider
      value={{
        partStartTime,
        interval,
        videoLength,
        songId,
        songVideoId,
        isPlayingEntire,
        setPartStartTime,
        plusPartInterval,
        minusPartInterval,
        toggleEntirePlaying,
      }}
    >
      {children}
    </CollectingPartContext.Provider>
  );
};
