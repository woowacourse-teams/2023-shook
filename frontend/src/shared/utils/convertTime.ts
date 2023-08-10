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

export const calculateMinSec = (
  minute: number,
  second: number,
  calculateFn: (originalSecond: number) => number
) => {
  return secondsToMinSec(calculateFn(minSecToSeconds({ minute, second })));
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

export const covertTwoDigitTimeFormat = (content: string | number) => {
  if (typeof content === 'number') {
    return content.toString().padStart(2, '0');
  }

  return content.padStart(2, '0');
};

export const covertTwoDigitTimeFormatText = (...contents: (string | number)[]) => {
  return contents.map(covertTwoDigitTimeFormat).join(':');
};

export const isValidTimeFormatLength = (value: string | number) => {
  if (typeof value === 'number') {
    return value.toString().length <= 2;
  }

  return value.length <= 2;
};
