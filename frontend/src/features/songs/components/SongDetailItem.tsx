import { forwardRef } from 'react';
import { styled } from 'styled-components';
import KillingPartInterface from '@/features/songs/components/KillingPartInterface';
import Thumbnail from '@/features/songs/components/Thumbnail';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import TimerProvider from '@/shared/components/Timer/TimerProvider';
import type { SongDetail } from '@/shared/types/song';

interface SongDetailItemProps extends SongDetail {}

const SongDetailItem = forwardRef<HTMLDivElement, SongDetailItemProps>(
  ({ id, killingParts, singer, title, songVideoId, albumCoverUrl }, ref) => {
    return (
      <Container ref={ref} role="article" data-song-id={id}>
        <SRHeading>킬링파트 듣기 페이지</SRHeading>
        <SongInfoContainer>
          <Thumbnail src={albumCoverUrl} size="md" />
          <Info>
            <SongTitle aria-label={`노래 ${title}`}>{title}</SongTitle>
            <Singer aria-label={`가수 ${singer}`}>{singer}</Singer>
          </Info>
        </SongInfoContainer>
        <Spacing direction="vertical" size={20} />
        <VideoPlayerProvider>
          <Youtube videoId={songVideoId} />
          <Spacing direction="vertical" size={16} />
          <TimerProvider time={15}>
            <KillingPartInterface killingParts={killingParts} songId={id} />
          </TimerProvider>
        </VideoPlayerProvider>
      </Container>
    );
  }
);

SongDetailItem.displayName = 'SongDetailItem';

export default SongDetailItem;

const Container = styled(Flex)`
  flex-direction: column;
  height: 100%;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    scroll-margin: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }
`;

const SongInfoContainer = styled.div`
  overflow: hidden;
  display: flex;
  gap: 12px;
  align-items: center;

  width: 100%;

  text-overflow: ellipsis;
  white-space: nowrap;
`;

const Info = styled.div``;

const SongTitle = styled.div`
  height: 30px;
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

const Singer = styled.div`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;
