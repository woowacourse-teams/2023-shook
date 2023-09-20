import { useCallback, useEffect, useState } from 'react';
import { useLoginPopup } from '@/features/auth/hooks/LoginPopUpContext';
import AuthError from '@/shared/remotes/AuthError';

const useFetch = <T>(fetcher: () => Promise<T>, defaultFetch: boolean = true) => {
  const [data, setData] = useState<T | null>(null);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<Error | null>(null);
  const { popupLoginModal } = useLoginPopup();

  if (error) {
    throw error;
  }

  const fetchData = useCallback(async () => {
    setError(null);
    setIsLoading(true);

    try {
      const data = await fetcher();
      setData(data);
    } catch (error) {
      console.log('in useFetch', 'error is...', error);
      if (error instanceof AuthError) {
        popupLoginModal();
        return;
      }
      setError(error as Error);
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
