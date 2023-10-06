// NOTE: ms단위입니다. (SECOND - 1000ms)
const LESS_THAN_MINUTE = 0;
const SECOND = 1000;
const MINUTE = 60;
const HOUR = 60 * MINUTE;
const DAY = 24 * HOUR;
const WEEK = 7 * DAY;
const MONTH = 30 * DAY;
const YEAR = 365 * DAY;
const MORE_THAN_YEAR = Infinity;

const convertRelativeTimeString = (createdAt: string, now = new Date().toISOString()) => {
  const rtf = new Intl.RelativeTimeFormat('ko', { numeric: 'always' });

  const createdTime = new Date(createdAt).getTime();
  const nowTime = new Date(now).getTime();
  const deltaSeconds = (createdTime - nowTime) / SECOND;

  const units: Intl.RelativeTimeFormatUnit[] = [
    'second',
    'minute',
    'hour',
    'day',
    'week',
    'month',
    'year',
  ];
  const cutoffs = [LESS_THAN_MINUTE, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR, MORE_THAN_YEAR];

  const unitIndex = cutoffs.findIndex((cutoff) => cutoff > Math.abs(deltaSeconds));
  const divisor = cutoffs[unitIndex - 1];

  if (divisor === LESS_THAN_MINUTE) {
    return '방금 전';
  }
  return rtf.format(Math.ceil(deltaSeconds / divisor), units[unitIndex - 1]);
};

export default convertRelativeTimeString;
