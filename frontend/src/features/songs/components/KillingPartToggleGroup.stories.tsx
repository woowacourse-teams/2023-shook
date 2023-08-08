import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import KillingPartToggleGroup from './KillingPartToggleGroup';
import { VoteInterfaceProvider } from './VoteInterfaceProvider';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof KillingPartToggleGroup> = {
  component: KillingPartToggleGroup,
  title: 'KillingPartToggleGroup',
  decorators: [
    (Story) => (
      <VideoPlayerProvider>
        <VoteInterfaceProvider videoLength={180} songId={1}>
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
