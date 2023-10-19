import ToggleSwitch from './ToggleSwitch';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: ToggleSwitch,
  title: 'shared/ToggleSwitch',
} satisfies Meta<typeof ToggleSwitch>;

export default meta;

type Story = StoryObj<typeof ToggleSwitch>;

export const Default = {
  render: () => (
    <ToggleSwitch
      id="1"
      on={() => {
        alert('on');
      }}
      off={() => {
        alert('off');
      }}
    />
  ),
} satisfies Story;
