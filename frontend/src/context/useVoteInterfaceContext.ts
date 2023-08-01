import { useContext } from 'react';
import { VoteInterfaceContext } from './VoteInterfaceProvider';

const useVoteInterfaceContext = () => {
  const voteInterfaceValues = useContext(VoteInterfaceContext);
  if (!voteInterfaceValues) throw new Error('VoteInterfaceContext에 value가 제공되지 않았습니다.');

  return { ...voteInterfaceValues };
};

export default useVoteInterfaceContext;
