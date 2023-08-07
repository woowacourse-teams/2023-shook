import { useState } from 'react';
import { css, styled } from 'styled-components';
import { calculateMinSec, minSecToSeconds, secondsToMinSec } from '@/shared/utils/convertTime';
import { isValidMinSec } from '@/shared/utils/validateTime';
import ERROR_MESSAGE from '../constants/errorMessage';
import useVoteInterfaceContext from '../hooks/useVoteInterfaceContext';
import { isInputName } from '../types/IntervalInput.type';
import type { IntervalInputType } from '../types/IntervalInput.type';

export interface IntervalInputProps {
  videoLength: number;
  errorMessage: string;
  onChangeErrorMessage: (message: string) => void;
}

const IntervalInput = ({ videoLength, errorMessage, onChangeErrorMessage }: IntervalInputProps) => {
  const { interval, partStartTime, updatePartStartTime } = useVoteInterfaceContext();

  const [activeInput, setActiveInput] = useState<IntervalInputType>(null);
  const { minute: startMinute, second: startSecond } = partStartTime;

  const [endMinute, endSecond] = calculateMinSec(
    startMinute,
    startSecond,
    (origin: number) => origin + interval
  );

  const onChangeIntervalStart: React.ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { name: timeUnit, value },
  }) => {
    if (!isValidMinSec(value)) {
      onChangeErrorMessage(ERROR_MESSAGE.MIN_SEC);

      return;
    }

    onChangeErrorMessage('');
    updatePartStartTime(timeUnit, Number(value));
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

const IntervalContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  padding: 0 24px;
  font-size: 16px;
  color: ${({ theme: { color } }) => color.white};
`;

const Flex = styled.div`
  display: flex;
`;

const ErrorMessage = styled.p`
  font-size: 12px;
  margin: 8px 0;
  color: ${({ theme: { color } }) => color.error};
`;

const Separator = styled.span<{ $inactive?: boolean }>`
  flex: none;
  text-align: center;
  margin: 0 8px;
  padding-bottom: 8px;
  color: ${({ $inactive, theme: { color } }) => $inactive && color.subText};
`;

const inputBase = css`
  flex: 1;
  margin: 0 8px;
  border: none;
  -webkit-box-shadow: none;
  box-shadow: none;
  margin: 0;
  padding: 0;
  background-color: transparent;
  text-align: center;
  border-bottom: 1px solid white;
  outline: none;
  width: 16px;
`;

const InputStart = styled.input<{ $active: boolean }>`
  ${inputBase}
  color: ${({ theme: { color } }) => color.white};
  border-bottom: 1px solid
    ${({ $active, theme: { color } }) => ($active ? color.primary : color.white)};
`;

const InputEnd = styled.input`
  ${inputBase}
  color: ${({ theme: { color } }) => color.subText};
  border-bottom: 1px solid ${({ theme: { color } }) => color.subText};
`;
