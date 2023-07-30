import { useState } from 'react';
import useKillingPartInterval from '../KillingPartToggleGroup/hooks/useKillingPartInterval';
import IntervalInput from './IntervalInput';
import type { TimeMinSec } from './IntervalInput.type';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: IntervalInput,
  title: 'IntervalInput',
} satisfies Meta<typeof IntervalInput>;

export default meta;

type Story = StoryObj<typeof IntervalInput>;

const TestIntervalInput = () => {
  const videoLength = 210;
  const [partStartTime, setPartStartTime] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const [errorMessage, setErrorMessage] = useState('');
  const { interval } = useKillingPartInterval();

  const onChangePartStart = (name: string, value: number) => {
    setPartStartTime({
      ...partStartTime,
      [name]: Number(value),
    });
  };

  const onChangeErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  return (
    <IntervalInput
      videoLength={videoLength}
      partStartTime={partStartTime}
      interval={interval}
      errorMessage={errorMessage}
      onChangePartStartTime={onChangePartStart}
      onChangeErrorMessage={onChangeErrorMessage}
    />
  );
};

export const Default = {
  render: () => <TestIntervalInput />,
} satisfies Story;
