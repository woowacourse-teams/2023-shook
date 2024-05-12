import { useCallback, useEffect, useState } from 'react';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { useLoginModalByError } from '@/features/auth/hooks/useLoginModalByError';
import AuthError from '@/shared/remotes/AuthError';
import type { ErrorResponse } from '../types/errorResponse';

const useFetch = <T>(fetcher: () => Promise<T>, defaultFetch: boolean = true) => {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ErrorResponse | null>(null);
  const { openLoginModalByError } = useLoginModalByError();
  const { logout } = useAuthContext();

  // TODO: Error Boudary 적용 시에 주석을 사용해주세요.
  // if (error) {
  //   throw error;
  // }

  const fetchData = useCallback(async () => {
    setError(null);
    setIsLoading(true);

    try {
      const data = await fetcher();
      setData(data);
    } catch (error) {
      if (error instanceof AuthError) {
        logout();
        openLoginModalByError(error.code);
        return;
      }
      setError(error as ErrorResponse);
    } finally {
      setIsLoading(false);
    }
  }, [fetcher]);

  useEffect(() => {
    if (!defaultFetch) return;

    fetchData();
  }, [defaultFetch]);

  return { data, isLoading, error, fetchData };
};

export default useFetch;
