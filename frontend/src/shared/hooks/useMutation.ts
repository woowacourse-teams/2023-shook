import { useCallback, useState } from 'react';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import { useLoginModalByError } from '@/features/auth/hooks/useLoginModalByError';
import AuthError from '@/shared/remotes/AuthError';
import type { ErrorResponse } from '../types/errorResponse';

/**
 * @deprecated react-query의 useMutation 훅을 사용해주세요.
 */
// eslint-disable-next-line
export const useMutation = <T, P extends any[]>(mutateFn: (...params: P) => Promise<T>) => {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<ErrorResponse | null>(null);
  const { openLoginModalByError } = useLoginModalByError();
  const { logout } = useAuthContext();

  // TODO: Error Boudary 적용 시에 주석을 사용해주세요.
  // if (error) {
  //   throw error;
  // }

  const mutateData = useCallback(
    async (...params: P) => {
      setError(null);
      setIsLoading(true);

      try {
        const responseBody = await mutateFn(...params);
        setData(responseBody);
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
    },
    [mutateFn]
  );

  return { data, isLoading, error, mutateData };
};
