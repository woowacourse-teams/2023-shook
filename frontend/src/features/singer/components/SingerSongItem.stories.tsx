import searchedSingers from '@/mocks/fixtures/searchedSingers.json';
import SingerSongItem from './SingerSongItem';
import type { Meta, StoryObj } from '@storybook/react';

const song = searchedSingers[0].songs[0];

const meta = {
  component: SingerSongItem,
  title: 'SingerSongItem',
} satisfies Meta<typeof SingerSongItem>;

export default meta;
type Story = StoryObj<typeof SingerSongItem>;

export const Default: Story = {
  render: () => <SingerSongItem {...song} />,
};
