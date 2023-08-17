import popularSongs from '@/mocks/fixtures/popularSongs.json';
import PopularSongItem from './PopularSongItem';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof PopularSongItem> = {
  component: PopularSongItem,
};

export default meta;

type Story = StoryObj<typeof PopularSongItem>;

const { title, singer, albumCoverUrl, totalLikeCount } = popularSongs[0];

export const Default: Story = {
  args: {
    rank: 1,
    title,
    singer,
    albumCoverUrl,
    totalLikeCount,
  },
};
