import formatOrdinals from '@/shared/utils/formatOrdinals';

describe('formatOrdinals', () => {
  test('숫자 1은 1st를 반환한다.', () => {
    expect(formatOrdinals(1)).toBe('1st');
  });

  test('숫자 2은 1nd를 반환한다.', () => {
    expect(formatOrdinals(2)).toBe('2nd');
  });

  test('숫자 3은 1rd를 반환한다.', () => {
    expect(formatOrdinals(3)).toBe('3rd');
  });

  test('숫자 4은 4th를 반환한다.', () => {
    expect(formatOrdinals(4)).toBe('4th');
  });

  test('숫자 11은 11th를 반환한다.', () => {
    expect(formatOrdinals(11)).toBe('11th');
  });
});
