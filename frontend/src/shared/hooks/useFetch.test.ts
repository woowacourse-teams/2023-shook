import { renderHook } from '@testing-library/react-hooks';
import useFetch from './useFetch';

describe('useFetch 테스트', () => {
  let fetcher: jest.Mock;

  beforeEach(() => {
    fetcher = jest.fn();
  });

  test('fetch가 성공했을 경우, isLoading 은 false, data는 mockData, error은 null이다.', async () => {
    const mockData = { key: 'value' };
    fetcher.mockResolvedValueOnce(mockData);
    const { result, waitForNextUpdate } = renderHook(() => useFetch(fetcher, true));

    expect(result.current.isLoading).toBe(true);

    await waitForNextUpdate();

    expect(result.current.isLoading).toBe(false);
    expect(result.current.data).toEqual(mockData);
    expect(result.current.error).toBe(null);
  });

  test('fetch가 실패했을 경우, isLoading 은 false, datas는 null, error은 mockError다.', async () => {
    const mockError = { message: 'An error occurred' };
    fetcher.mockRejectedValueOnce(mockError);
    const { result, waitForNextUpdate } = renderHook(() => useFetch(fetcher, true));

    expect(result.current.isLoading).toBe(true);

    await waitForNextUpdate();

    expect(result.current.isLoading).toBe(false);
    expect(result.current.data).toBe(null);
    expect(result.current.error).toEqual(mockError);
  });
});
