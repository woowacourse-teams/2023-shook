import { useState } from 'react';
import { calculateMinSec, minSecToSeconds, secondsToMinSec } from '@/utils/convertTime';
import { isValidMinSec } from '@/utils/validateTime';
import ERROR_MESSAGE from './constants/errorMessage';
import {
  ErrorMessage,
  InputEnd,
  Flex,
  InputStart,
  IntervalContainer,
  Separator,
} from './IntervalInput.style';
import { isInputName } from './IntervalInput.type';
import type { IntervalInputType, TimeMinSec } from './IntervalInput.type';
import type { KillingPartInterval } from '../KillingPartToggleGroup';

export interface IntervalInputProps {
  videoLength: number;
  partStartTime: TimeMinSec;
  errorMessage: string;
  interval: KillingPartInterval;
  onChangeErrorMessage: (message: string) => void;
  onChangePartStartTime: (name: string, value: number) => void;
}

const IntervalInput = ({
  videoLength,
  partStartTime,
  errorMessage,
  interval,
  onChangePartStartTime,
  onChangeErrorMessage,
}: IntervalInputProps) => {
  const [activeInput, setActiveInput] = useState<IntervalInputType>(null);
  const { minute: startMinute, second: startSecond } = partStartTime;

  const [endMinute, endSecond] = calculateMinSec(
    startMinute,
    startSecond,
    (origin: number) => origin + interval
  );

  const onChangeIntervalStart: React.ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { name, value },
  }) => {
    if (!isValidMinSec(value)) {
      onChangeErrorMessage(ERROR_MESSAGE.MIN_SEC);

      return;
    }

    onChangeErrorMessage('');
    onChangePartStartTime(name, Number(value));
  };

  const onFocusIntervalStart: React.FocusEventHandler<HTMLInputElement> = ({
    currentTarget: { name },
  }) => {
    if (isInputName(name)) {
      setActiveInput(name);
    }
  };

  const onBlurIntervalStart = () => {
    const timeSelected = minSecToSeconds([startMinute, startSecond]);

    if (timeSelected > videoLength - interval) {
      const [songMin, songSec] = secondsToMinSec(videoLength - interval);

      onChangeErrorMessage(ERROR_MESSAGE.SONG_RANGE(songMin, songSec));
      return;
    }

    onChangeErrorMessage('');
    setActiveInput(null);
  };

  return (
    <IntervalContainer>
      <Flex>
        <InputStart
          name="minute"
          value={startMinute}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onFocus={onFocusIntervalStart}
          placeholder="0"
          autoComplete="off"
          inputMode="numeric"
          $active={activeInput === 'minute'}
        />
        <Separator>:</Separator>
        <InputStart
          name="second"
          value={startSecond}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onFocus={onFocusIntervalStart}
          placeholder="0"
          autoComplete="off"
          inputMode="numeric"
          $active={activeInput === 'second'}
        />
        <Separator> ~ </Separator>
        <InputEnd value={endMinute} disabled />
        <Separator $inactive>:</Separator>
        <InputEnd value={endSecond} disabled />
      </Flex>
      <ErrorMessage role="alert">{errorMessage}</ErrorMessage>
    </IntervalContainer>
  );
};

export default IntervalInput;
