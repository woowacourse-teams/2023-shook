import { useContext } from 'react';
import { TimerContext } from '../TimerProvider';

const useTimerContext = () => {
  const TimerContextValues = useContext(TimerContext);
  if (!TimerContextValues) throw new Error('TimerContext에 value가 제공되지 않았습니다.');

  return { ...TimerContextValues };
};

export default useTimerContext;
