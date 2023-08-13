import type { TimeMinSec } from '@/features/songs/types/IntervalInput.type';

export const minSecToSeconds = ({ minute, second }: TimeMinSec) => {
  return minute * 60 + second;
};

export const secondsToMinSec = (seconds: number): TimeMinSec => {
  return {
    minute: Math.floor(seconds / 60),
    second: seconds % 60,
  };
};

export const toMinSecText = (seconds: number) => {
  const { minute, second } = secondsToMinSec(seconds);

  const minText = minute.toString().padStart(2, '0');
  const secText = second.toString().padStart(2, '0');

  return `${minText}:${secText}`;
};

export const toPlayingTimeText = (startTime: number, endTime: number) => {
  return `${toMinSecText(startTime)} ~ ${toMinSecText(endTime)}`;
};
