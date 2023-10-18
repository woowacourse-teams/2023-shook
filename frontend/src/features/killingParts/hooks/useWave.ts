import { useRef, useState } from 'react';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';

const useWave = () => {
  const { interval, videoLength, partStartTime, setPartStartTime, isPlayingEntire } =
    useCollectingPartContext();
  const video = useVideoPlayerContext();
  const boxRef = useRef<HTMLDivElement>(null);
  const [xPos, setXPos] = useState<{ initial: number; scroll: number } | null>(null);

  const maxPartStartTime = videoLength - interval;
  const progressWidth = 100 + (interval - 5) * 5;
  const isInterval = video.playerState === YT.PlayerState.PLAYING && !isPlayingEntire;
  const isEntire = video.playerState === YT.PlayerState.PLAYING && isPlayingEntire;

  const scrollStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;

    if (!boxRef.current) return;
    const clientWidth = boxRef.current?.clientWidth;
    const unit = (scrollWidth - clientWidth) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);
    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      setPartStartTime(partStartTimeToChange);
    }
  };

  const wheelStartTime: React.WheelEventHandler<HTMLDivElement> = (e) => {
    console.log('[wheel]');
    if (!boxRef.current) return;

    if (Math.abs(e.deltaX) < Math.abs(e.deltaY)) {
      e.currentTarget?.scrollTo({
        left: e.deltaY / 1.2 + boxRef.current?.scrollLeft,
        behavior: 'smooth',
      });
    }
  };

  const playVideo = () => {
    if (video.playerState !== YT.PlayerState.PLAYING) {
      video.play();
    }
  };

  const dragStart: React.MouseEventHandler<HTMLDivElement> = (e) => {
    e.preventDefault();
    if (boxRef.current) {
      setXPos({
        initial: e.screenX,
        scroll: boxRef.current?.scrollLeft,
      });
    }
  };

  const dragMoving: React.MouseEventHandler<HTMLDivElement> = ({ screenX }) => {
    if (!xPos) return;

    boxRef.current?.scrollTo({
      left: xPos.scroll + (xPos.initial - screenX) / 0.3,
      behavior: 'instant',
    });
  };

  const dragEnd = () => {
    setXPos(null);
  };

  useDebounceEffect(() => video.seekTo(partStartTime), [partStartTime], 300);
  useDebounceEffect(
    () => {
      if (boxRef.current) {
        const unit =
          (boxRef.current.scrollWidth - boxRef.current.clientWidth) / (videoLength - interval);
        boxRef.current.scrollTo({
          left: partStartTime * unit + 2.5,
          behavior: 'instant',
        });
      }
      video.seekTo(partStartTime);
    },
    [interval],
    300
  );

  return {
    boxRef,
    progressWidth,
    interval,
    maxPartStartTime,
    isInterval,
    isEntire,
    scrollStartTime,
    wheelStartTime,
    playVideo,
    dragStart,
    dragMoving,
    dragEnd,
  };
};

export default useWave;
