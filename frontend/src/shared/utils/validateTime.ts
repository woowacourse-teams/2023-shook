export const isValidMinSec = (timeInput: string) => /^(?:[0-5]?[0-9])?$/.test(timeInput);

export const isValidTimeFormatLength = (value: string | number) => {
  if (typeof value === 'number') {
    return value.toString().length <= 2;
  }

  return value.length <= 2;
};
