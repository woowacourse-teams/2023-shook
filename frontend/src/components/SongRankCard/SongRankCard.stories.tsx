import popularSongs from '@/mocks/fixtures/popularSongs.json';
import SongRankCard from './SongRankCard';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof SongRankCard> = {
  component: SongRankCard,
};

export default meta;

type Story = StoryObj<typeof SongRankCard>;

const { thumbnail, title, singer } = popularSongs[0];

export const Default: Story = {
  args: {
    rank: 1,
    thumbnail,
    title,
    singer,
  },
};
