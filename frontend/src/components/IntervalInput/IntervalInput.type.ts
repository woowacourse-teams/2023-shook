export interface IntervalInputProps {
  songEnd: number;
}

export interface TimeMinSec {
  minute: number;
  second: number;
}

export type IntervalInputType = null | 'minute' | 'second';

export const isInputName = (name: unknown): name is IntervalInputType => {
  return name === 'minute' || name === 'second' || name === null;
};
