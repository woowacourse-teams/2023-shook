import { act, renderHook } from '@testing-library/react';
import useModal from './useModal';

describe('useModal 훅 테스트입니다.', () => {
  test('isOpen의 초기값은 false다', () => {
    const { result } = renderHook(() => useModal());

    expect(result.current.isOpen).toBe(false);
  });

  test('openModal로 isOpen 상태를 true로 바꿀 수 있다.', () => {
    const { result } = renderHook(() => useModal(false));

    act(() => {
      result.current.openModal();
    });

    expect(result.current.isOpen).toBe(true);
  });

  test('closeModal로 isOpen 상태를 false로 바꿀 수 있다.', () => {
    const { result } = renderHook(() => useModal(false));

    act(() => {
      result.current.openModal();
    });

    expect(result.current.isOpen).toBe(true);

    act(() => {
      result.current.closeModal();
    });

    expect(result.current.isOpen).toBe(false);
  });
});
