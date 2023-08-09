import {
  covertTwoDigitTimeFormat,
  getTwoDigitTimeFormatText,
  isValidTimeFormatLength,
  minSecToSeconds,
  secondsToMinSec,
} from './convertTime';

describe('minSecToSeconds: 분/초 정보를 바탕으로 초 단위로 변환', () => {
  test('2분 30초를 받으면 150초를 반환한다.', () => {
    expect(minSecToSeconds([2, 30])).toBe(150);
  });

  test('0분 45초를 받으면 45초를 반환한다.', () => {
    expect(minSecToSeconds([0, 45])).toBe(45);
  });
});

describe('secondsToMinSec:', () => {
  test('150초를 받으면 2분 30초를 반환한다.', () => {
    expect(secondsToMinSec(150)).toEqual([2, 30]);
  });

  test('45초를 받으면 0분 45초를 반환한다.', () => {
    expect(secondsToMinSec(45)).toEqual([0, 45]);
  });
});

describe('isValidateTimeFormatLength 함수 검증 테스트', () => {
  test.each(['1', '22', '33'])('길이 2 이하의 string type 인자라면 true를 반환한다.', (args) => {
    expect(isValidTimeFormatLength(args)).toBe(true);
  });

  test.each(['101', '111', '121'])(
    '길이 2 초과의 string type 인자라면 false를 반환한다.',
    (args) => {
      expect(isValidTimeFormatLength(args)).toBe(false);
    }
  );

  test.each([1, 22, 33])('길이 2 이하의 number type 인자라면 true를 반환한다.', (args) => {
    expect(isValidTimeFormatLength(args)).toBe(true);
  });

  test.each([101, 111, 121])('길이 2 초과의 number type 인자라면 false를 반환한다.', (args) => {
    expect(isValidTimeFormatLength(args)).toBe(false);
  });
});

describe('addLeadingZeroForTimeFormat 함수 검증 테스트', () => {
  test('길이 2 미만의 string type 인자라면 인자의 맨 앞에 0을 붙여 문자열로 반환한다.', () => {
    const result = covertTwoDigitTimeFormat('1');
    const expected = '01';

    expect(result).toBe(expected);
  });

  test('길이 2의 string type 인자라면 그대로 반환한다.', () => {
    const result = covertTwoDigitTimeFormat('12');
    const expected = '12';

    expect(result).toBe(expected);
  });

  test('길이 2 미만의 number type 인자라면 맨 앞에 0을 붙여 문자열로 반환한다.', () => {
    const result = covertTwoDigitTimeFormat(2);
    const expected = '02';

    expect(result).toBe(expected);
  });

  test('길이 2의 number type 인자라면 그대로 문자열로 반환한다.', () => {
    const result = covertTwoDigitTimeFormat(12);
    const expected = '12';

    expect(result).toBe(expected);
  });
});

describe('getTimeFormatText 함수 검증 테스트', () => {
  test('길이 2 이하의 string or number type 인자에 길이가 2가 되도록 앞에서부터 0을 붙이고, 각 요소 사이에 : 구분자를 붙여 시간 포멧 텍스트로 return한다.', () => {
    const result = getTwoDigitTimeFormatText('1', 22);
    const expected = '01:22';

    expect(result).toBe(expected);
  });
});
