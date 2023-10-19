import Header from '@/shared/components/Layout/Header';
import SearchBar from './SearchBar';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: SearchBar,
  title: 'search/SearchBar',
} satisfies Meta<typeof SearchBar>;

export default meta;
type Story = StoryObj<typeof SearchBar>;

export const Default: Story = {
  render: () => <Header />,
};
