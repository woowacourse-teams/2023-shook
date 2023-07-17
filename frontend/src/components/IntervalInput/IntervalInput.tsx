import { useState } from 'react';
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
      setErrorMessage('0 ~ 59 의 숫자만 입력 가능해요');
      return;
    }

    setErrorMessage('');
    setIntervalStart({
      ...intervalStart,
      [name]: Number(value),
    });
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
          placeholder="0"
          autoComplete="off"
        />
      </div>
      <p>{errorMessage}</p>
    </div>
  );
};

export default IntervalInput;
