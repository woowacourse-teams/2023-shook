import { useState } from 'react';
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
    setIntervalStart((prev) => ({
      ...prev,
      [name]: value,
    }));
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
        />
        <span>:</span>
        <label htmlFor="start-sec" />
        <input
          id="start-sec"
          name="second"
          value={intervalStart.second}
          onChange={onChangeIntervalStart}
        />
      </div>
      <p>{errorMessage}</p>
    </div>
  );
};

export default IntervalInput;
