import { createContext, useEffect, useMemo, useRef, useState } from 'react';
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
  pinList: Pin[];
  activePinIndex: number;
  waveScrubberRef: React.RefObject<HTMLDivElement>;
  forceScrollWave: () => void;
  triggerScrollKey: () => void;
  setPinList: React.Dispatch<React.SetStateAction<Pin[]>>;
  setInterval: React.Dispatch<React.SetStateAction<number>>;
  increasePartInterval: () => void;
  decreasePartInterval: () => void;
  toggleEntirePlaying: () => void;
}

interface Pin {
  partStartTime: number;
  interval: number;
  text: string;
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
  const [pinList, setPinList] = useState<Pin[]>([]);
  const [scrollKey, setScrollKey] = useState<number>(0);
  const waveScrubberRef = useRef<HTMLDivElement>(null);
  const activePinIndex = useMemo(
    () => pinList.findIndex((pin) => pin.partStartTime === partStartTime),
    [pinList, partStartTime]
  );

  const triggerScrollKey = () => {
    setScrollKey((prevKey) => prevKey + 1);
  };

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

  const forceScrollWave = () => {
    if (waveScrubberRef.current) {
      const unit =
        (waveScrubberRef.current.scrollWidth - waveScrubberRef.current.clientWidth) /
        (videoLength - interval);
      waveScrubberRef.current.scrollTo({
        left: partStartTime * unit + 2.5,
        behavior: 'instant',
      });
    }
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

  useEffect(() => {
    forceScrollWave();
  }, [scrollKey]);

  return (
    <CollectingPartContext.Provider
      value={{
        partStartTime,
        interval,
        videoLength,
        songId,
        songVideoId,
        isPlayingEntire,
        pinList,
        activePinIndex,
        waveScrubberRef,
        triggerScrollKey,
        forceScrollWave,
        setPinList,
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
