import { useState } from 'react';
import { complyToMinSec, minSecToSeconds, secondsToMinSec } from '@/utils/convertTime';
import { isTimeInSongRange, isValidMinSec } from '@/utils/validateTime';
import ERROR_MESSAGE from '../constants/errorMessage';
import { isInputName } from '../IntervalInput.type';
import type { IntervalInputType, TimeMinSec } from '../IntervalInput.type';
import type { ChangeEventHandler, FocusEventHandler } from 'react';

const useIntervalInput = (songEnd: number) => {
  const [intervalStart, setIntervalStart] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const [errorMessage, setErrorMessage] = useState('');
  const [activeInput, setActiveInput] = useState<IntervalInputType>(null);
  const { minute: startMinute, second: startSecond } = intervalStart;

  const [endMinute, endSecond] = complyToMinSec(
    startMinute,
    startSecond,
    (origin: number) => origin + 10
  );

  const onChangeIntervalStart: ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { name, value },
  }) => {
    if (!isValidMinSec(value)) {
      setErrorMessage(ERROR_MESSAGE.MIN_SEC);
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
    const timeSelected = minSecToSeconds(startMinute, startSecond);

    if (!isTimeInSongRange({ songEnd, timeSelected })) {
      const [songMin, songSec] = secondsToMinSec(songEnd - 10);
      setErrorMessage(ERROR_MESSAGE.SONG_RANGE(songMin, songSec));
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
