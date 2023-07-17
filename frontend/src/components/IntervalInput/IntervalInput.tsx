import { useState } from 'react';
import { minSecToSeconds, secondsToMinSec } from '@/utils/convertTime';
import { isTimeInSongRange, isValidMinSec } from '@/utils/validateTime';
import {
  ErrorMessage,
  InputEnd,
  InputFlex,
  InputStart,
  IntervalContainer,
  Separator,
} from './IntervalInput.style';
import { isInputName } from './IntervalInput.type';
import type { IntervalInputProps, IntervalInputType, TimeMinSec } from './IntervalInput.type';
import type { ChangeEventHandler, MouseEventHandler } from 'react';

const IntervalInput = ({ songEnd }: IntervalInputProps) => {
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

  const onClickIntervalStart: MouseEventHandler<HTMLInputElement> = ({
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

  return (
    <IntervalContainer>
      <InputFlex>
        <label htmlFor="start-min" />
        <InputStart
          id="start-min"
          name="minute"
          value={intervalStart.minute}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onClick={onClickIntervalStart}
          placeholder="0"
          autoComplete="off"
          active={activeInput === 'minute'}
        />
        <Separator>:</Separator>
        <label htmlFor="start-sec" />
        <InputStart
          id="start-sec"
          name="second"
          value={intervalStart.second}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onClick={onClickIntervalStart}
          placeholder="0"
          autoComplete="off"
          active={activeInput === 'second'}
        />
        <Separator> ~ </Separator>
        <InputEnd value={endMinute} disabled />
        <Separator inactive>:</Separator>
        <label htmlFor="start-sec" />
        <InputEnd value={endSecond} disabled />
      </InputFlex>
      <ErrorMessage role="alert">{errorMessage}</ErrorMessage>
    </IntervalContainer>
  );
};

export default IntervalInput;
