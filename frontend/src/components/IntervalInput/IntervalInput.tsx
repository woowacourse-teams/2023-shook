import { useState } from 'react';
import { css, styled } from 'styled-components';
import { minSecToSeconds, secondsToMinSec } from '@/utils/convertTime';
import { isTimeInSongRange, isValidMinSec } from '@/utils/validateTime';
import type { ChangeEventHandler } from 'react';
interface IntervalInputProps {
  songEnd: number;
}

interface TimeMinSec {
  minute: number;
  second: number;
}

const IntervalInput = ({ songEnd }: IntervalInputProps) => {
  const [intervalStart, setIntervalStart] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const [errorMessage, setErrorMessage] = useState('');

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

  const onBlurIntervalStart = () => {
    const timeSelected = minSecToSeconds(intervalStart.minute, intervalStart.second);

    if (!isTimeInSongRange({ songEnd, timeSelected })) {
      const [songMin, songSec] = secondsToMinSec(songEnd - 10);
      setErrorMessage(`구간 시작을 ${songMin}분 ${songSec}초보다 전으로 설정해주세요`);
    }
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
          placeholder="0"
          autoComplete="off"
          active
        />
        <Separator>:</Separator>
        <label htmlFor="start-sec" />
        <InputStart
          id="start-sec"
          name="second"
          value={intervalStart.second}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          placeholder="0"
          autoComplete="off"
          active
        />
        <Separator> ~ </Separator>
        <InputEnd value={endMinute} disabled />
        <Separator>:</Separator>
        <label htmlFor="start-sec" />
        <InputEnd value={endSecond} disabled />
      </InputFlex>
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
  border: 1px solid red;

  font-size: 16px;

  color: white;
`;

const InputFlex = styled.div`
  display: flex;
`;

const ErrorMessage = styled.p`
  font-size: 14px;
  color: #f5222d;
`;

const Separator = styled.span`
  box-sizing: border-box;
  margin: 0 5px;
  padding-bottom: 8px;
`;

const inputBase = css`
  flex: 1;
  margin: 0 5px;

  border: none;
  -webkit-box-shadow: none;
  box-shadow: none;

  margin: 0;
  padding: 0;

  background-color: transparent;
  text-align: center;

  border-bottom: 1px solid white;

  width: 10px;
`;

const InputStart = styled.input<{ active: boolean }>`
  ${inputBase}
  color: white;
  border-bottom: 1px solid ${({ active }) => (active ? '#DE2F5F' : 'white')};
`;

const InputEnd = styled.input`
  ${inputBase}
  color: #a7a7a7;
  border-bottom: 1px solid #a7a7a7;
`;
