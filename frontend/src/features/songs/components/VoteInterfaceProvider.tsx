import { createContext, useEffect, useState } from 'react';
import { MAX_PART_INTERVAL, MIN_PART_INTERVAL } from '@/features/songs/constants/partInterval';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import type { PropsWithChildren } from 'react';

interface VoteInterfaceContextProps extends VoteInterfaceProviderProps {
  partStartTime: number;
  interval: number;
  // NOTE: Why both setState and eventHandler have same naming convention?
  updatePartStartTime: (timeUnit: string, value: number) => void;
  updateKillingPartInterval: React.MouseEventHandler<HTMLButtonElement>;
  plusPartInterval: () => void;
  minusPartInterval: () => void;
}

export const VoteInterfaceContext = createContext<VoteInterfaceContextProps | null>(null);

interface VoteInterfaceProviderProps {
  videoLength: number;
  songId: number;
  songVideoId: string;
}

export const VoteInterfaceProvider = ({
  children,
  videoLength,
  songId,
  songVideoId,
}: PropsWithChildren<VoteInterfaceProviderProps>) => {
  const [interval, setInterval] = useState(10);
  const [partStartTime, setPartStartTime] = useState(0);
  const { videoPlayer } = useVideoPlayerContext();

  const updateKillingPartInterval: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    const newInterval = Number(e.currentTarget.dataset['interval']) as number;
    const partEndTime = partStartTime + newInterval;

    if (partEndTime > videoLength) {
      const overflowedSeconds = partEndTime - videoLength;
      setPartStartTime(partStartTime - overflowedSeconds);
    }

    setInterval(newInterval);
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

  const updatePartStartTime = (timeUnit: string, value: number) => {
    if (timeUnit === 'minute') {
      setPartStartTime((prev) => {
        const minute = 60 * value;
        const second = prev % 60;

        return minute + second;
      });
    }

    if (timeUnit === 'second') {
      setPartStartTime((prev) => {
        const minute = 60 * Math.floor(prev / 60);
        const second = value;

        return minute + second;
      });
    }
  };

  useEffect(() => {
    const timer = window.setInterval(() => {
      videoPlayer.current?.seekTo(partStartTime, true);
    }, interval * 1000);

    return () => window.clearInterval(timer);
  }, [videoPlayer.current, partStartTime, interval]);

  return (
    <VoteInterfaceContext.Provider
      value={{
        partStartTime,
        interval,
        videoLength,
        songId,
        songVideoId,
        updatePartStartTime,
        updateKillingPartInterval,
        plusPartInterval,
        minusPartInterval,
      }}
    >
      {children}
    </VoteInterfaceContext.Provider>
  );
};
