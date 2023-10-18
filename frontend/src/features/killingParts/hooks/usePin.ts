import { useMemo, useRef, useState } from 'react';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import { toMinSecText } from '@/shared/utils/convertTime';

interface Pin {
  partStartTime: number;
  interval: number;
  text: string;
}

const usePin = () => {
  const { partStartTime, interval, setPartStartTime, setInterval } = useCollectingPartContext();
  const [pinList, setPinList] = useState<Pin[]>([]);
  const ref = useRef<HTMLDivElement>(null);

  const activePinIndex = useMemo(
    () =>
      pinList.findIndex((pin) => pin.partStartTime === partStartTime && pin.interval === interval),
    [pinList, partStartTime, interval]
  );

  const isPinListEmpty = pinList.length === 0;

  const pinAnimationRef = useRef<number>(1);
  const refreshPinAnimation = () => {
    pinAnimationRef.current += 1;
  };

  const addPin = () => {
    const text = `${toMinSecText(partStartTime)}`;

    setPinList((prevTimeList) => [
      {
        partStartTime,
        interval,
        text,
      },
      ...prevTimeList.filter((pin) => pin.text !== text),
    ]);

    if (ref.current) {
      ref.current.scrollTo({
        left: 0,
        behavior: 'smooth',
      });
    }

    refreshPinAnimation();
  };

  const deletePin = () => {
    if (activePinIndex >= 0) {
      setPinList(pinList.filter((_, index) => index !== activePinIndex));
    } else {
      setPinList(pinList.slice(1));
    }
  };

  const playPin = (start: number, interval: number) => () => {
    setPartStartTime(start);
    setInterval(interval);
  };

  return {
    pinList,
    isPinListEmpty,
    activePinIndex,
    pinAnimationRef,
    ref,
    addPin,
    deletePin,
    playPin,
  };
};

export default usePin;
