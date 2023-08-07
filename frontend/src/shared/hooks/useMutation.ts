import { useState } from 'react';
import type { ErrorResponse } from '@/remotes';

// eslint-disable-next-line
export const useMutation = <T, P extends any[]>(mutateFn: (...params: P) => Promise<T>) => {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ErrorResponse | null>(null);

  const mutateData = async (...params: P) => {
    setError(null);
    setIsLoading(true);

    try {
      const responseBody = await mutateFn(...params);
      setData(responseBody);
    } catch (error) {
      setError(error as ErrorResponse);
    } finally {
      setIsLoading(false);
    }
  };

  return { data, isLoading, error, mutateData };
};
