import { useState } from 'react';
import { minSecToSeconds, secondsToMinSec } from '@/utils/convertTime';
import { isTimeInSongRange, isValidMinSec } from '@/utils/validateTime';
import { isInputName } from '../IntervalInput.type';
import type { IntervalInputType, TimeMinSec } from '../IntervalInput.type';
import type { ChangeEventHandler, FocusEventHandler } from 'react';

const useIntervalInput = (songEnd: number) => {
  const [intervalStart, setIntervalStart] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const [errorMessage, setErrorMessage] = useState('');
  const [activeInput, setActiveInput] = useState<IntervalInputType>(null);

  const [endMinute, endSecond] = secondsToMinSec(
    minSecToSeconds(intervalStart.minute, intervalStart.second) + 10
  );

  const onChangeIntervalStart: ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { name, value },
  }) => {
    if (!isValidMinSec(value)) {
      setErrorMessage('초/분은 0 ~ 59 숫자만 입력 가능해요');
      return;
    }

    setErrorMessage('');
    setIntervalStart({
      ...intervalStart,
      [name]: Number(value),
    });
  };

  const onFocusIntervalStart: FocusEventHandler<HTMLInputElement> = ({
    currentTarget: { name },
  }) => {
    if (isInputName(name)) {
      setActiveInput(name);
    }
  };

  const onBlurIntervalStart = () => {
    const timeSelected = minSecToSeconds(intervalStart.minute, intervalStart.second);

    if (!isTimeInSongRange({ songEnd, timeSelected })) {
      const [songMin, songSec] = secondsToMinSec(songEnd - 10);
      setErrorMessage(`구간 시작을 ${songMin}분 ${songSec}초보다 전으로 설정해주세요`);
    }

    setActiveInput(null);
  };

  return {
    intervalStart,
    errorMessage,
    activeInput,
    endMinute,
    endSecond,
    onChangeIntervalStart,
    onFocusIntervalStart,
    onBlurIntervalStart,
  };
};

export default useIntervalInput;
