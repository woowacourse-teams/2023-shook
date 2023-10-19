import searchedSingers from '@/mocks/fixtures/searchedSingers.json';
import SingerBanner from './SingerBanner';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: SingerBanner,
  title: 'singer/SingerBanner',
} satisfies Meta<typeof SingerBanner>;

export default meta;
type Story = StoryObj<typeof SingerBanner>;

export const Default: Story = {
  render: () => <SingerBanner {...searchedSingers[0]} />,
};
