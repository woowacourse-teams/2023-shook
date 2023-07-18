import { isValidMinSec } from './validateTime';

describe('isValidMinSec: 초/분 양식이 맞는지 확인하는 함수', () => {
  test('순자외 문자열은 false를 반환한다.', () => {
    expect(isValidMinSec('한글')).toBe(false);
    expect(isValidMinSec('English')).toBe(false);
  });

  test('십진법 이외의 진법 문자열은 false를 반환한다.', () => {
    expect(isValidMinSec('0x12312')).toBe(false);
    expect(isValidMinSec('0b12312')).toBe(false);
    expect(isValidMinSec('0o12312')).toBe(false);
  });

  test('0미만 혹은 60 이상 자연수는 false를 반환한다.', () => {
    expect(isValidMinSec('-1')).toBe(false);
    expect(isValidMinSec('0')).toBe(true);
    expect(isValidMinSec('00')).toBe(true);
    expect(isValidMinSec('60')).toBe(false);
    expect(isValidMinSec('1000000')).toBe(false);
  });

  test('0~59 사이의 자연수은 true를 반환한다.', () => {
    expect(isValidMinSec('0')).toBe(true);
    expect(isValidMinSec('30')).toBe(true);
  });

  test('소수는 false를 반환한다.', () => {
    expect(isValidMinSec('32.4')).toBe(false);
    expect(isValidMinSec('0.4')).toBe(false);
  });

  test('빈문자열은 true를 반환한다.', () => {
    expect(isValidMinSec('')).toBe(true);
  });
});
