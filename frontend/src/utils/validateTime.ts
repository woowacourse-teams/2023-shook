export const isValidMinSec = (timeInput: string) => /^(?:[0-5]?[0-9])?$/.test(timeInput);

export const isTimeInSongRange = ({
  timeSelected,
  songEnd,
  interval = 10,
}: {
  timeSelected: number;
  songEnd: number;
  interval?: number;
}) => {
  return timeSelected <= songEnd - interval;
};
