import { useSearchParams } from 'react-router-dom';

const useValidSearchParams = (...params: string[]) => {
  const [searchParams] = useSearchParams();

  const validParams = params.map((param) => {
    const validParam = searchParams.get(param);
    if (validParam === null) throw new Error('Invalid search parameters');

    return validParam;
  });

  return validParams;
};

export default useValidSearchParams;
