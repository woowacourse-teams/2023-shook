import IntervalInput from './IntervalInput';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: IntervalInput,
  title: 'IntervalInput',
} satisfies Meta<typeof IntervalInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default = {
  args: {
    videoLength: 195,
  },
  render: ({ videoLength }) => {
    return (
      <div style={{ backgroundColor: 'black' }}>
        <IntervalInput videoLength={videoLength} />
      </div>
    );
  },
} satisfies Story;
