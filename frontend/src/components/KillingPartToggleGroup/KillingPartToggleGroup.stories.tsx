import useKillingPartInterval from './hooks/useKillingPartInterval';
import KillingPartToggleGroup from './KillingPartToggleGroup';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof KillingPartToggleGroup> = {
  component: KillingPartToggleGroup,
};

export default meta;

type Story = StoryObj<typeof KillingPartToggleGroup>;

const Example = () => {
  const { interval, setKillingPartInterval } = useKillingPartInterval();

  return (
    <KillingPartToggleGroup interval={interval} setKillingPartInterval={setKillingPartInterval} />
  );
};

export const Default: Story = {
  render: () => <Example />,
};
