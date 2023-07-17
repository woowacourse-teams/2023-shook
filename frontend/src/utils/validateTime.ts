export const isValidMinSec = (timeInput: string) => /^(?:[0-5]?[0-9])?$/.test(timeInput);

export const isTimeInSongRange = ({
  timeInput,
  songEnd,
  interval = 10,
}: {
  timeInput: string;
  songEnd: number;
  interval?: number;
}) => {
  console.log(songEnd >= Number(timeInput) + interval);
  return songEnd >= Number(timeInput) + interval;
};
