import { useState } from 'react';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import TimerProvider from '@/shared/components/Timer/TimerProvider';
import ToastProvider from '@/shared/components/Toast/ToastProvider';
import KillingPartTrack from './KillingPartTrack';
import type { KillingPart } from '@/shared/types/song';
import type { Meta, StoryObj } from '@storybook/react';

const meta = {
  component: KillingPartTrack,
  title: 'KillingPartTrack',
  decorators: [
    (Story) => {
      return (
        <ToastProvider>
          <VideoPlayerProvider>
            <TimerProvider time={killingPart.end - killingPart.start}>
              <Story />
            </TimerProvider>
          </VideoPlayerProvider>
        </ToastProvider>
      );
    },
  ],
} satisfies Meta<typeof KillingPartTrack>;

export default meta;
type Story = StoryObj<typeof KillingPartTrack>;

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

const KillingPartTrackWithHook = () => {
  const [nowPlayingTrack, setNowPlayingTrack] = useState<KillingPart['id']>(-1);

  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [commentsPartId, setCommentsPartId] = useState<KillingPart['id']>(-1);

  const isNowPlayingTrack = killingPart.id === nowPlayingTrack;

  return (
    <KillingPartTrack
      killingPart={killingPart}
      songId={1}
      isNowPlayingTrack={isNowPlayingTrack}
      setNowPlayingTrack={setNowPlayingTrack}
      setCommentsPartId={setCommentsPartId}
    />
  );
};

export const Default: Story = {
  render: () => <KillingPartTrackWithHook />,
};
