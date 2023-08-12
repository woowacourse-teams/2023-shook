import Timer from './Timer';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: Timer,
  title: 'Timer',
} satisfies Meta<typeof Timer>;

export default meta;
type Story = StoryObj<typeof Timer>;

export const Default: Story = {
  render: () => <Timer />,
};
