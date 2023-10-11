import { useSearchParams } from 'react-router-dom';

const useValidSearchParams = (...params: string[]) => {
  const [searchParams] = useSearchParams();

  const validParams: Record<string, string> = {};

  params.forEach((param) => {
    const validParam = searchParams.get(param);
    if (validParam === null) throw new Error('Invalid search parameters');

    validParams[param] = validParam;
  });

  return validParams;
};

export default useValidSearchParams;
