import convertRelativeTimeString from './convertRelativeTimeString';

describe('ISO-8601 형식의 string을 받아 현재 시간과 비교해서 상대시간을 반환한다.', () => {
  // 상대 시간 기준은 n <= 상대 시간 < n + 1 입니다.
  test('차이가 없을 경우 방금 전을 반환한다.', () => {
    const now = new Date().toISOString();

    expect(convertRelativeTimeString(now)).toBe('방금 전');
  });

  test('차이가 1분 미만의 경우 방금 전을 반환한다.', () => {
    const createdAt = '2023-10-06T09:59:59.999';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('방금 전');
  });

  test('차이가 1시간 미만인 경우 n분 전을 반환한다', () => {
    const createdAt = '2023-10-06T09:50:10';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('9분 전');
  });

  test('차이가 1일 미만인 경우 n시간 전을 반환한다.', () => {
    const createdAt = '2023-10-06T08:12:00';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('1시간 전');
  });

  test('차이가 1주 미만인 경우 n일 전을 반환한다.', () => {
    const createdAt = '2023-10-03T10:00:00';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('3일 전');
  });

  test('차이가 1달 미만인 경우 n주 전을 반환한다.', () => {
    const createdAt = '2023-09-11T10:00:00';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('3주 전');
  });

  test('차이가 1년 미만인 경우 n개월 전을 반환한다.', () => {
    // 1달을 30일로 계산하기 때문에 오차가 약간 있습니다.
    const createdAt = '2023-06-08T10:00:00';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('4개월 전');
  });

  test('차이가 1년 이상인 경우 n년 전을 반환한다.', () => {
    // 3년이 되기 직전 값으로 최대값으로 볼 수 있습니다.
    const createdAt = '2020-10-06T10:00:00.001';
    const now = '2023-10-06T10:00:00';

    expect(convertRelativeTimeString(createdAt, now)).toBe('2년 전');
  });
});
