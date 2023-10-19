import { useState } from 'react';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';

const useWave = () => {
  const {
    partStartTime,
    interval,
    videoLength,
    setPartStartTime,
    isPlayingEntire,
    waveScrubberRef,
    scrollingRef,
  } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const [xPos, setXPos] = useState<{ initial: number; scroll: number } | null>(null);

  const maxPartStartTime = videoLength - interval;
  const progressWidth = 100 + (interval - 5) * 5;
  const isInterval = video.playerState === YT.PlayerState.PLAYING && !isPlayingEntire;
  const isEntire = video.playerState === YT.PlayerState.PLAYING && isPlayingEntire;

  const scrollStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;

    if (!waveScrubberRef.current) return;

    const clientWidth = waveScrubberRef.current?.clientWidth;
    const unit = (scrollWidth - clientWidth) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);

    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      setPartStartTime(partStartTimeToChange);
    }

    scrollingRef.current = window.setTimeout(() => {
      scrollingRef.current = null;
    }, 300);
  };

  const wheelStartTime: React.WheelEventHandler<HTMLDivElement> = (e) => {
    if (!waveScrubberRef.current) return;

    if (Math.abs(e.deltaX) < Math.abs(e.deltaY)) {
      e.currentTarget?.scrollTo({
        left: e.deltaY / 1.2 + waveScrubberRef.current?.scrollLeft,
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
    if (waveScrubberRef.current) {
      setXPos({
        initial: e.screenX,
        scroll: waveScrubberRef.current?.scrollLeft,
      });
    }
  };

  const dragMoving: React.MouseEventHandler<HTMLDivElement> = ({ screenX }) => {
    if (!xPos) return;

    waveScrubberRef.current?.scrollTo({
      left: xPos.scroll + (xPos.initial - screenX) / 0.3,
      behavior: 'instant',
    });
  };

  const dragEnd = () => {
    setXPos(null);
  };

  useDebounceEffect(() => video.seekTo(partStartTime), [interval, partStartTime], 200);

  return {
    waveScrubberRef,
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
