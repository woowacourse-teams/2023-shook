import { useCallback, useEffect } from 'react';

const useDebounceEffect = <D>(fn: () => void, deps: D, delay: number = 500) => {
  const callback = useCallback(fn, [deps]);

  useEffect(() => {
    const timer = window.setTimeout(() => {
      callback();
    }, delay);

    return () => window.clearTimeout(timer);
  }, [delay, callback]);
};

export default useDebounceEffect;
