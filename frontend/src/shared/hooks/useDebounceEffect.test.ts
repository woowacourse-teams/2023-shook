import { renderHook, act } from '@testing-library/react';
import useDebounceEffect from './useDebounceEffect';

jest.useFakeTimers();

describe('useDebounceEffect 테스트. debounce 제한 딜레이를 0.5초로 둘 경우', () => {
  test('0.499초 지났을 때, 한 번도 실행되지 않는다.', () => {
    const fn = jest.fn();
    const { rerender } = renderHook(({ deps, delay }) => useDebounceEffect(fn, deps, delay), {
      initialProps: { deps: 'dependency1', delay: 500 },
    });

    expect(fn).not.toBeCalled();

    rerender({ deps: 'dependency2', delay: 500 });
    rerender({ deps: 'dependency3', delay: 500 });
    rerender({ deps: 'dependency4', delay: 500 });

    act(() => {
      jest.advanceTimersByTime(499);
    });

    expect(fn).not.toBeCalled();
  });

  test('0.5초 지났을 때, 한 번만 실행된다.', () => {
    const fn = jest.fn();
    const { rerender } = renderHook(({ deps, delay }) => useDebounceEffect(fn, deps, delay), {
      initialProps: { deps: 'dependency1', delay: 500 },
    });

    expect(fn).not.toBeCalled();

    rerender({ deps: 'dependency2', delay: 500 });
    rerender({ deps: 'dependency3', delay: 500 });
    rerender({ deps: 'dependency4', delay: 500 });

    act(() => {
      jest.advanceTimersByTime(500);
    });

    expect(fn).toBeCalledTimes(1);
  });
});
