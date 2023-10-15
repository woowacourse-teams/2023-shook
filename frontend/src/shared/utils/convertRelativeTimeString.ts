// NOTE: 상수는 모두 ms단위 입니다.
const SECOND = 1000;

// NOTE: cutoff를 위한 상수들입니다.
const LESS_THAN_MINUTE = 0;
const MINUTE = 60 * SECOND;
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
  const deltaTime = createdTime - nowTime;

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

  const unitIndex = cutoffs.findIndex((cutoff) => cutoff > Math.abs(deltaTime));
  const divisor = cutoffs[unitIndex - 1];

  if (divisor === LESS_THAN_MINUTE) {
    return '방금 전';
  }
  return rtf.format(Math.ceil(deltaTime / divisor), units[unitIndex - 1]);
};

export default convertRelativeTimeString;
