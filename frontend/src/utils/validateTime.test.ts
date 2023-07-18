import { isValidMinSec } from './validateTime';

describe('isValidMinSec: 초/분 양식이 맞는지 확인하는 함수', () => {
  test('한글은 false를 반환한다.', () => {
    expect(isValidMinSec('한글')).toBe(false);
  });

  test('0x12312은 false를 반환한다.', () => {
    expect(isValidMinSec('0x12312')).toBe(false);
  });

  test('-1은 false를 반환한다.', () => {
    expect(isValidMinSec('-1')).toBe(false);
  });

  test('61은 false를 반환한다.', () => {
    expect(isValidMinSec('61')).toBe(false);
  });

  test('0.4은 false를 반환한다.', () => {
    expect(isValidMinSec('0.4')).toBe(false);
  });

  test('32.4은 false를 반환한다.', () => {
    expect(isValidMinSec('32.4')).toBe(false);
  });

  test('0은 true를 반환한다.', () => {
    expect(isValidMinSec('0')).toBe(true);
  });

  test('00은 true를 반환한다.', () => {
    expect(isValidMinSec('00')).toBe(true);
  });

  test('빈문자열은 true를 반환한다.', () => {
    expect(isValidMinSec('')).toBe(true);
  });

  test('30은 true를 반환한다.', () => {
    expect(isValidMinSec('30')).toBe(true);
  });
});
