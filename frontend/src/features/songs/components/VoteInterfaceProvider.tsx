import { createContext, useEffect, useState } from 'react';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import { minSecToSeconds, secondsToMinSec } from '@/shared/utils/convertTime';
import type { TimeMinSec } from '../types/IntervalInput.type';
import type { KillingPartInterval } from '../types/KillingPartToggleGroup.type';
import type { PropsWithChildren } from 'react';

interface VoteInterfaceContextProps extends VoteInterfaceProviderProps {
  partStartTime: TimeMinSec;
  interval: KillingPartInterval;
  updatePartStartTime: (timeUnit: string, value: number) => void;
  updateKillingPartInterval: React.MouseEventHandler<HTMLButtonElement>;
}

export const VoteInterfaceContext = createContext<VoteInterfaceContextProps | null>(null);

interface VoteInterfaceProviderProps {
  videoLength: number;
  songId: number;
}

export const VoteInterfaceProvider = ({
  children,
  videoLength,
  songId,
}: PropsWithChildren<VoteInterfaceProviderProps>) => {
  const [interval, setInterval] = useState<KillingPartInterval>(10);
  const [partStartTime, setPartStartTime] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const { videoPlayer } = useVideoPlayerContext();

  const updateKillingPartInterval: React.MouseEventHandler<HTMLButtonElement> = (e) => {
    const partStartTimeInSeconds = minSecToSeconds([partStartTime.minute, partStartTime.second]);
    const newInterval = Number(e.currentTarget.dataset['interval']) as KillingPartInterval;

    const partEndTimeInSeconds = partStartTimeInSeconds + newInterval;

    if (partEndTimeInSeconds > videoLength) {
      const overflowedSeconds = partEndTimeInSeconds - videoLength;
      const [fixedStartMinute, fixedStartSecond] = secondsToMinSec(
        partStartTimeInSeconds - overflowedSeconds
      );

      setPartStartTime({ minute: fixedStartMinute, second: fixedStartSecond });
    }

    setInterval(newInterval);
  };

  const updatePartStartTime = (timeUnit: string, value: number) => {
    setPartStartTime((prev) => ({ ...prev, [timeUnit]: value }));
  };

  useEffect(() => {
    const timer = window.setInterval(() => {
      const startSecond = minSecToSeconds([partStartTime.minute, partStartTime.second]);

      videoPlayer?.seekTo(startSecond, true);
    }, interval * 1000);

    return () => window.clearInterval(timer);
  }, [videoPlayer, partStartTime, interval]);

  return (
    <VoteInterfaceContext.Provider
      value={{
        partStartTime,
        interval,
        videoLength,
        songId,
        updatePartStartTime,
        updateKillingPartInterval,
      }}
    >
      {children}
    </VoteInterfaceContext.Provider>
  );
};
