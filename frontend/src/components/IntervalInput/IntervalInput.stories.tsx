import { useState } from 'react';
import { VoteInterfaceProvider } from '@/context/VoteInterfaceProvider';
import IntervalInput from './IntervalInput';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: IntervalInput,
  title: 'IntervalInput',
  decorators: [
    (Story) => (
      <VoteInterfaceProvider>
        <Story />
      </VoteInterfaceProvider>
    ),
  ],
} satisfies Meta<typeof IntervalInput>;

export default meta;

type Story = StoryObj<typeof IntervalInput>;

const TestIntervalInput = () => {
  const videoLength = 210;
  const [errorMessage, setErrorMessage] = useState('');

  const onChangeErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  return (
    <IntervalInput
      videoLength={videoLength}
      errorMessage={errorMessage}
      onChangeErrorMessage={onChangeErrorMessage}
    />
  );
};

export const Default = {
  render: () => <TestIntervalInput />,
} satisfies Story;
