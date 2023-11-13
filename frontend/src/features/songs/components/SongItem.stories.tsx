import highLikedSongs from '@/mocks/fixtures/highLikedSongs.json';
import SongItem from './SongItem';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof SongItem> = {
  component: SongItem,
  title: 'songs/SongItem',
};

export default meta;

type Story = StoryObj<typeof SongItem>;

const { title, singer, albumCoverUrl, totalLikeCount } = highLikedSongs[0];

export const Default: Story = {
  args: {
    rank: 1,
    title,
    singer,
    albumCoverUrl,
    totalLikeCount,
  },
};
