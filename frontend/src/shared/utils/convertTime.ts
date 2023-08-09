export const minSecToSeconds = ([minute, second]: [minute: number, second: number]) => {
  return minute * 60 + second;
};

export const secondsToMinSec = (seconds: number): [minute: number, second: number] => {
  return [Math.floor(seconds / 60), seconds % 60];
};

export const calculateMinSec = (
  minute: number,
  second: number,
  calculateFn: (originalSecond: number) => number
) => {
  return secondsToMinSec(calculateFn(minSecToSeconds([minute, second])));
};

export const getPlayingTimeText = (startTime: number, endTime: number) => {
  const [startMin, startSec, endMin, endSec] = [
    ...secondsToMinSec(startTime),
    ...secondsToMinSec(endTime),
  ].map((timeSec) => {
    const timeString = timeSec.toString();

    if (timeString.length === 1) {
      return `0${timeString}`;
    } else {
      return timeString;
    }
  });

  return `${startMin}:${startSec} ~ ${endMin}:${endSec}`;
};

export const covertTwoDigitTimeFormat = (content: string | number) => {
  if (typeof content === 'number') {
    return content.toString().padStart(2, '0');
  }

  return content.padStart(2, '0');
};

export const getTwoDigitTimeFormatText = (...contents: (string | number)[]) => {
  return contents.map(covertTwoDigitTimeFormat).join(':');
};

export const isValidTimeFormatLength = (value: string | number) => {
  if (typeof value === 'number') {
    return value.toString().length <= 2;
  }

  return value.length <= 2;
};
