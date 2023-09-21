import { useParams } from 'react-router-dom';

const useValidParams = <T extends Record<string, string | undefined>>() => {
  const params = useParams<T>();
  if (!params || Object.values(params).some((value) => value === undefined)) {
    throw new Error('Invalid parameters');
  }

  return params as Record<string, string>;
};

export default useValidParams;
