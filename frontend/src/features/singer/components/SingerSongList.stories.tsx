import singerSongs from '@/mocks/fixtures/searchedSingers.json';
import SingerSongList from './SingerSongList';
import type { Meta, StoryObj } from '@storybook/react';

const singerSong = singerSongs[0];

const meta = {
  component: SingerSongList,
  title: 'singer/SingerSongList',
} satisfies Meta<typeof SingerSongList>;

export default meta;
type Story = StoryObj<typeof SingerSongList>;

export const Default: Story = {
  render: () => <SingerSongList songs={singerSong.songs} />,
};
