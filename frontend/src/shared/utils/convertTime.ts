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
