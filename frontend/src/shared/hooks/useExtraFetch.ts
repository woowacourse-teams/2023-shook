import { useCallback, useState } from 'react';
import type { ErrorResponse } from '@/shared/remotes';

type FetchDirection = 'top' | 'down';

const useExtraFetch = <T, P>(
  extraFetcher: (...params: P[]) => Promise<T[]>,
  fetchDirection: FetchDirection
) => {
  const [data, setData] = useState<T[]>([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ErrorResponse | null>(null);

  const fetchData = useCallback(
    async (...params: P[]) => {
      setError(null);
      setIsLoading(true);

      try {
        const extraData = await extraFetcher(...params);

        if (fetchDirection === 'top') {
          const newData = extraData.concat(data);
          setData(newData);
          return;
        }

        const newData = data?.concat(extraData);
        console.log();
        setData(newData);
      } catch (error) {
        setError(error as ErrorResponse);
      } finally {
        setIsLoading(false);
      }
    },
    [data, extraFetcher, fetchDirection]
  );

  return { data, isLoading, error, fetchData };
};

export default useExtraFetch;

// TODO: 현 fetch기준으로 코드 전반적인 에러처리 구조 생각해보기
// useXXX는 fetcher에서 throw한 에러를 state에 넣어 return하고 이걸 컴포넌트에서 분기로 처리하는 구조.
// 서버에서 내려주는 커스텀 에러코드, 메세지 객체 활용 고민
// ex) Error class
