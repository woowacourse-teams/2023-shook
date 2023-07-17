import { minSecToSeconds, secondsToMinSec } from './convertTime';

describe('minSecToSeconds: 분/초 정보를 바탕으로 초 단위로 변환', () => {
  test('2분 30초는 150초이다.', () => {
    expect(minSecToSeconds(2, 30)).toBe(150);
  });

  test('0분 45초는 45초이다.', () => {
    expect(minSecToSeconds(0, 45)).toBe(45);
  });
});

describe('secondsToMinSec:', () => {
  test('150초는 2분 30초다.', () => {
    expect(secondsToMinSec(150)).toEqual([2, 30]);
  });

  test('45초는 0분 45초다', () => {
    expect(secondsToMinSec(45)).toEqual([0, 45]);
  });
});
