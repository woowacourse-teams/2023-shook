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
  // NOTE: 상대 시간 기준을 초과하는지 오름차순으로 확인하기 위한 배열입니다.
  const cutoffs = [LESS_THAN_MINUTE, MINUTE, HOUR, DAY, WEEK, MONTH, YEAR, MORE_THAN_YEAR];

  // NOTE: 기준을 초과하는 요소 바로 전 요소가 상대 시간의 기준이 되는 요소입니다.
  const unitIndex = cutoffs.findIndex((cutoff) => cutoff > Math.abs(deltaTime)) - 1;
  const divisor = cutoffs[unitIndex];

  if (divisor === LESS_THAN_MINUTE) {
    return '방금 전';
  }
  return rtf.format(Math.ceil(deltaTime / divisor), units[unitIndex]);
};

export default convertRelativeTimeString;
