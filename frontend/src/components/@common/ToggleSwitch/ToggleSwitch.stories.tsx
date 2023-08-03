import ToggleSwitch from './ToggleSwitch';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: ToggleSwitch,
  title: 'ToggleSwitch',
} satisfies Meta<typeof ToggleSwitch>;

export default meta;

type Story = StoryObj<typeof ToggleSwitch>;

export const Default = {
  render: () => (
    <ToggleSwitch
      on={() => {
        alert('on');
      }}
      off={() => {
        alert('off');
      }}
    />
  ),
} satisfies Story;
