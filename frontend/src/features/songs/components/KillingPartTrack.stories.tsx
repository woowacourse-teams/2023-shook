import KillingPartTrack from './KillingPartTrack';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: KillingPartTrack,
  title: 'KillingPartTrack',
} satisfies Meta<typeof KillingPartTrack>;

export default meta;
type Story = StoryObj<typeof KillingPartTrack>;

export const Default: Story = {
  render: () => <KillingPartTrack />,
};
