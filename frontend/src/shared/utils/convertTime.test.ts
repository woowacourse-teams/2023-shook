import { minSecToSeconds, secondsToMinSec, toMinSecText, toPlayingTimeText } from './convertTime';

describe('minSecToSeconds: 분/초 정보를 바탕으로 초 단위로 변환', () => {
  test('2분 30초를 받으면 150초를 반환한다.', () => {
    expect(minSecToSeconds({ minute: 2, second: 30 })).toBe(150);
  });

  test('0분 45초를 받으면 45초를 반환한다.', () => {
    expect(minSecToSeconds({ minute: 0, second: 45 })).toBe(45);
  });
});

describe('secondsToMinSec: 초 정보를 바탕으로 분/초 단위로 변환', () => {
  test('150초를 받으면 2분 30초를 반환한다.', () => {
    expect(secondsToMinSec(150)).toEqual({ minute: 2, second: 30 });
  });

  test('45초를 받으면 0분 45초를 반환한다.', () => {
    expect(secondsToMinSec(45)).toEqual({ minute: 0, second: 45 });
  });
});

describe('toMinSecText는 초를 받아 "mm:ss" 형식으로 텍스트 변환을 해준다.', () => {
  test('0초는 00:00을 표시한다.', () => {
    expect(toMinSecText(0)).toBe('00:00');
  });

  test('62초는 01:02을 표시한다.', () => {
    expect(toMinSecText(62)).toBe('01:02');
  });

  test('3599초는 59:59을 표시한다.', () => {
    expect(toMinSecText(3599)).toBe('59:59');
  });
});

describe('toPlayingTimeText는 시작과 끝의 초를 받아 "mm:ss ~ mm:ss" 형식으로 텍스트 변환을 해준다.', () => {
  test('', () => {
    expect(toPlayingTimeText(52, 62)).toBe('00:52 ~ 01:02');
  });

  test('', () => {
    expect(toPlayingTimeText(62, 72)).toBe('01:02 ~ 01:12');
  });
});
