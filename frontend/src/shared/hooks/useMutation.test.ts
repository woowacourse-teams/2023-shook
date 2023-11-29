import { renderHook, act, waitFor } from '@testing-library/react';
import { useMutation } from './useMutation';

describe('useMutation 테스트', () => {
  let mutateFn: jest.Mock;

  beforeEach(() => {
    mutateFn = jest.fn();
  });

  test('fetch가 성공했을 경우, isLoading 은 false, data는 mockData, error은 null이다.', async () => {
    const mockData = { key: 'value' };
    mutateFn.mockResolvedValueOnce(mockData);
    const { result } = renderHook(() => useMutation(mutateFn));

    act(() => {
      result.current.mutateData();
    });

    expect(result.current.isLoading).toBe(true);

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
      expect(result.current.data).toEqual(mockData);
      expect(result.current.error).toBe(null);
    });
  });

  test('fetch가 실패했을 경우, isLoading 은 false, datas는 null, error은 mockError다.', async () => {
    const mockError = { message: 'An error occurred' };
    mutateFn.mockRejectedValueOnce(mockError);
    const { result } = renderHook(() => useMutation(mutateFn));

    act(() => {
      result.current.mutateData();
    });

    expect(result.current.isLoading).toBe(true);

    await waitFor(() => {
      expect(result.current.isLoading).toBe(false);
      expect(result.current.data).toBe(null);
      expect(result.current.error).toEqual(mockError);
    });
  });
});
