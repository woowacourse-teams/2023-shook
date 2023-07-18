export const minSecToSeconds = ([minute, second]: [minute: number, second: number]) =>
  minute * 60 + second;

export const secondsToMinSec = (seconds: number): [minute: number, second: number] => {
  return [Math.floor(seconds / 60), seconds % 60];
};

export const calculateMinSec = (minute: number, second: number, fn: (origin: number) => number) => {
  return secondsToMinSec(fn(minSecToSeconds([minute, second])));
};
