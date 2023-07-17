import Toast from './Toast';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: Toast,
  title: 'Toast',
  argTypes: {
    message: {
      control: 'text',
      description: '토스트에 표시될 메세지입니다.',
    },
  },
} satisfies Meta<typeof Toast>;

export default meta;
type Story = StoryObj<typeof Toast>;

export const Default: Story = {
  render: ({ message }) => <Toast message={message} />,
};
