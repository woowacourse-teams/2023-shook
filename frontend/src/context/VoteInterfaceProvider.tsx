import { createContext, useState } from 'react';
import useKillingPartInterval from '@/components/KillingPartToggleGroup/hooks/useKillingPartInterval';
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
  const [partStartTime, setPartStartTime] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const { interval, setKillingPartInterval } = useKillingPartInterval();

  const updatePartStartTime = (timeUnit: string, value: number) => {
    setPartStartTime((prev) => ({ ...prev, [timeUnit]: value }));
  };

  return (
    <VoteInterfaceContext.Provider
      value={{ partStartTime, interval, updatePartStartTime, setKillingPartInterval }}
    >
      {children}
    </VoteInterfaceContext.Provider>
  );
};
