export const minSecToSeconds = (minute: number, second: number) => minute * 60 + second;

export const secondsToMinSec = (seconds: number): [minute: number, second: number] => {
  return [Math.floor(seconds / 60), seconds % 60];
};
