import { useState } from 'react';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import ToastProvider from '@/shared/components/Toast/ToastProvider';
import KillingPartTrackList from './KillingPartTrackList';
import type { KillingPart } from '@/shared/types/song';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: KillingPartTrackList,
  title: 'KillingPartTrackList',
  decorators: [
    (Story) => {
      return (
        <ToastProvider>
          <VideoPlayerProvider>
            <Story />
          </VideoPlayerProvider>
        </ToastProvider>
      );
    },
  ],
} satisfies Meta<typeof KillingPartTrackList>;

export default meta;
type Story = StoryObj<typeof KillingPartTrackList>;

const killingPart: KillingPart = {
  id: 1,
  rank: 1,
  voteCount: 0,
  start: 70,
  end: 80,
  partVideoUrl: 'https://youtu.be/ArmDp-zijuc?start=105&end=115',
  likeCount: 12,
  likeStatus: false,
  partLength: 10,
};

const killingPart2: KillingPart = {
  id: 2,
  rank: 2,
  voteCount: 0,
  start: 70,
  end: 80,
  partVideoUrl: 'https://youtu.be/ArmDp-zijuc?start=105&end=115',
  likeCount: 12,
  likeStatus: false,
  partLength: 10,
};

const killingPart3: KillingPart = {
  id: 3,
  rank: 3,
  voteCount: 0,
  start: 70,
  end: 80,
  partVideoUrl: 'https://youtu.be/ArmDp-zijuc?start=105&end=115',
  likeCount: 12,
  likeStatus: false,
  partLength: 10,
};

const KillingPartTrackListWithHooks = () => {
  const [nowPlayingTrack, setNowPlayingTrack] = useState<KillingPart['id']>(-1);
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [commentsPartId, setCommentsPartId] = useState<KillingPart['id']>(-1);

  return (
    <KillingPartTrackList
      killingParts={[killingPart, killingPart2, killingPart3]}
      songId={1}
      nowPlayingTrack={nowPlayingTrack}
      setNowPlayingTrack={setNowPlayingTrack}
      setCommentsPartId={setCommentsPartId}
    />
  );
};

export const Default: Story = {
  render: () => <KillingPartTrackListWithHooks />,
};
