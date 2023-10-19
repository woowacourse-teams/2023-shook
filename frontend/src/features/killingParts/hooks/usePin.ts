import { useRef } from 'react';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import { toMinSecText } from '@/shared/utils/convertTime';

const usePin = () => {
  const {
    partStartTime,
    interval,
    setPartStartTime,
    setInterval,
    pinList,
    setPinList,
    activePinIndex,
    triggerScrollKey,
  } = useCollectingPartContext();

  const pinContainerRef = useRef<HTMLDivElement>(null);

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

    if (pinContainerRef.current) {
      pinContainerRef.current.scrollTo({
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
    triggerScrollKey();
  };

  return {
    pinList,
    isPinListEmpty,
    activePinIndex,
    pinAnimationRef,
    pinContainerRef,
    partStartTime,
    addPin,
    deletePin,
    playPin,
  };
};

export default usePin;
