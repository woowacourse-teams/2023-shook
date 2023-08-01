import { VoteInterfaceProvider } from '@/components/VoteInterface';
import KillingPartToggleGroup from './KillingPartToggleGroup';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof KillingPartToggleGroup> = {
  component: KillingPartToggleGroup,
  title: 'KillingPartToggleGroup',
  decorators: [
    (Story) => (
      <VoteInterfaceProvider>
        <Story />
      </VoteInterfaceProvider>
    ),
  ],
};

export default meta;

type Story = StoryObj<typeof KillingPartToggleGroup>;

export const Default: Story = {
  render: () => <KillingPartToggleGroup />,
};
