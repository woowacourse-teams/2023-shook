import { useCallback, useState } from 'react';
import type { ErrorResponse } from '@/shared/remotes';

// eslint-disable-next-line
export const useMutation = <T, P extends any[]>(mutateFn: (...params: P) => Promise<T>) => {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ErrorResponse | null>(null);

  const mutateData = useCallback(
    async (...params: P) => {
      setError(null);
      setIsLoading(true);

      if (error) {
        throw error;
      }

      try {
        const responseBody = await mutateFn(...params);
        setData(responseBody);
      } catch (error) {
        setError(error as ErrorResponse);
      } finally {
        setIsLoading(false);
      }
    },
    [mutateFn]
  );

  return { data, isLoading, error, mutateData };
};
