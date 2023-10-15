import { useContext } from 'react';
import { CollectingPartContext } from '../components/CollectingPartProvider';

const useCollectingPartContext = () => {
  const collectingPartValues = useContext(CollectingPartContext);

  if (!collectingPartValues) {
    throw new Error('CollectingPartContext의 value가 제공되지 않았습니다.');
  }

  return { ...collectingPartValues };
};

export default useCollectingPartContext;
