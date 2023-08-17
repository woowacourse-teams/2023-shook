import { useState } from 'react';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import IntervalInput from './IntervalInput';
import { VoteInterfaceProvider } from './VoteInterfaceProvider';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: IntervalInput,
  title: 'IntervalInput',
  decorators: [
    (Story) => (
      <VideoPlayerProvider>
        <VoteInterfaceProvider videoLength={180} songId={1}>
          <Story />
        </VoteInterfaceProvider>
      </VideoPlayerProvider>
    ),
  ],
} satisfies Meta<typeof IntervalInput>;

export default meta;

type Story = StoryObj<typeof IntervalInput>;

const TestIntervalInput = () => {
  const [errorMessage, setErrorMessage] = useState('');

  const onChangeErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  return <IntervalInput errorMessage={errorMessage} onChangeErrorMessage={onChangeErrorMessage} />;
};

export const Default = {
  render: () => <TestIntervalInput />,
} satisfies Story;
