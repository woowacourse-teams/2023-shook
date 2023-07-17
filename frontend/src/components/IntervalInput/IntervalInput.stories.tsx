import IntervalInput from './IntervalInput';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: IntervalInput,
  title: 'Header',
} satisfies Meta<typeof IntervalInput>;

export default meta;

type Story = StoryObj<typeof meta>;

export const Default = {
  args: {
    songEnd: 195,
  },
} satisfies Story;
