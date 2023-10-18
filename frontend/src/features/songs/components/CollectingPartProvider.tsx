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
  setInterval: React.Dispatch<React.SetStateAction<number>>;
  increasePartInterval: () => void;
  decreasePartInterval: () => void;
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
    if (isPlayingEntire) {
      seekTo(partStartTime);
    }
    setIsPlayingEntire(!isPlayingEntire);
  };

  const increasePartInterval = () => {
    if (interval === MAX_PART_INTERVAL) return;

    setInterval(interval + 1);
  };

  const decreasePartInterval = () => {
    if (interval === MIN_PART_INTERVAL) return;

    setInterval(interval - 1);
  };

  useEffect(() => {
    if (isPlayingEntire || playerState !== YT.PlayerState.PLAYING) return;

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
        setInterval,
        increasePartInterval,
        decreasePartInterval,
        toggleEntirePlaying,
      }}
    >
      {children}
    </CollectingPartContext.Provider>
  );
};
