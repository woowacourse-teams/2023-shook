import { useCallback, useState } from 'react';
import { useLoginPopup } from '@/features/auth/hooks/LoginPopUpContext';
import AuthError from '@/shared/remotes/AuthError';

// eslint-disable-next-line
export const useMutation = <T, P extends any[]>(mutateFn: (...params: P) => Promise<T>) => {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);
  const { popupLoginModal } = useLoginPopup();

  if (error) {
    throw error;
  }

  const mutateData = useCallback(
    async (...params: P) => {
      setError(null);
      setIsLoading(true);

      try {
        const responseBody = await mutateFn(...params);
        setData(responseBody);
      } catch (error) {
        console.log('in mutation', 'error is...', error);
        if (error instanceof AuthError) {
          popupLoginModal();
          return;
        }
        setError(error as Error);
      } finally {
        setIsLoading(false);
      }
    },
    [mutateFn]
  );

  return { data, isLoading, error, mutateData };
};
