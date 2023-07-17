import { useState } from 'react';
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
    <div>
      <div>
        <label htmlFor="start-min" />
        <input
          id="start-min"
          name="minute"
          value={intervalStart.minute}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          placeholder="0"
          autoComplete="off"
        />
        <span>:</span>
        <label htmlFor="start-sec" />
        <input
          id="start-sec"
          name="second"
          value={intervalStart.second}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          placeholder="0"
          autoComplete="off"
        />
      </div>
      <p role="alert">{errorMessage}</p>
    </div>
  );
};

export default IntervalInput;
