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
  toggleAllPlay: () => void;
  isAllPlay: boolean;
  isVideoStatePlaying: boolean;
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
  const [isAllPlay, setIsAllPlay] = useState(false);
  const { videoPlayer, playerState } = useVideoPlayerContext();

  const isVideoStatePlaying = playerState === 1;

  const updateKillingPartInterval: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    const newInterval = Number(e.currentTarget.dataset['interval']) as number;
    const partEndTime = partStartTime + newInterval;

    if (partEndTime > videoLength) {
      const overflowedSeconds = partEndTime - videoLength;
      setPartStartTime(partStartTime - overflowedSeconds);
    }

    setInterval(newInterval);
  };

  const toggleAllPlay = () => {
    setIsAllPlay((prev) => !prev);
    videoPlayer.current?.playVideo();
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
    if (isAllPlay) return;

    const timer = window.setInterval(() => {
      videoPlayer.current?.seekTo(partStartTime, true);
    }, interval * 1000);

    return () => {
      window.clearInterval(timer);
    };
  }, [playerState, partStartTime, interval, isAllPlay]);

  return (
    <VoteInterfaceContext.Provider
      value={{
        partStartTime,
        interval,
        videoLength,
        songId,
        songVideoId,
        isAllPlay,
        isVideoStatePlaying,
        updatePartStartTime,
        updateKillingPartInterval,
        plusPartInterval,
        minusPartInterval,
        toggleAllPlay,
      }}
    >
      {children}
    </VoteInterfaceContext.Provider>
  );
};
