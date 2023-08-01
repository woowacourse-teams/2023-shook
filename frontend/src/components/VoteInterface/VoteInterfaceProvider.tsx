import { createContext, useEffect, useState } from 'react';
import useKillingPartInterval from '@/components/KillingPartToggleGroup/hooks/useKillingPartInterval';
import useVideoPlayerContext from '@/context/useVideoPlayerContext';
import { minSecToSeconds } from '@/utils/convertTime';
import type { TimeMinSec } from '@/components/IntervalInput/IntervalInput.type';
import type { KillingPartInterval } from '@/components/KillingPartToggleGroup';
import type { PropsWithChildren } from 'react';

interface VoteInterfaceContextProps {
  partStartTime: TimeMinSec;
  interval: KillingPartInterval;
  updatePartStartTime: (timeUnit: string, value: number) => void;
  setKillingPartInterval: React.MouseEventHandler<HTMLButtonElement>;
}

export const VoteInterfaceContext = createContext<VoteInterfaceContextProps | null>(null);

export const VoteInterfaceProvider = ({ children }: PropsWithChildren) => {
  const { interval, setKillingPartInterval } = useKillingPartInterval();
  const [partStartTime, setPartStartTime] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const { videoPlayer } = useVideoPlayerContext();

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
        updatePartStartTime,
        setKillingPartInterval,
      }}
    >
      {children}
    </VoteInterfaceContext.Provider>
  );
};
