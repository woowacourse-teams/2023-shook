import { VoteInterfaceProvider } from '@/components/VoteInterface';
import { VideoPlayerProvider } from '../Youtube';
import KillingPartToggleGroup from './KillingPartToggleGroup';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof KillingPartToggleGroup> = {
  component: KillingPartToggleGroup,
  title: 'KillingPartToggleGroup',
  decorators: [
    (Story) => (
      <VideoPlayerProvider>
        <VoteInterfaceProvider>
          <Story />
        </VoteInterfaceProvider>
      </VideoPlayerProvider>
    ),
  ],
};

export default meta;

type Story = StoryObj<typeof KillingPartToggleGroup>;

export const Default: Story = {
  render: () => <KillingPartToggleGroup />,
};
