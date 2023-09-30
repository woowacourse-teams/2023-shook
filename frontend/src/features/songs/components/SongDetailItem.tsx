import { forwardRef } from 'react';
import { styled } from 'styled-components';
import KillingPartInterface from '@/features/songs/components/KillingPartInterface';
import Thumbnail from '@/features/songs/components/Thumbnail';
import { VideoPlayerProvider } from '@/features/youtube/components/VideoPlayerProvider';
import Youtube from '@/features/youtube/components/Youtube';
import Flex from '@/shared/components/Flex/Flex';
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
        <VideoPlayerProvider>
          <Flex
            $gap={16}
            $md={{ $direction: 'column' }}
            $css={{ padding: '16px', background: '#121212c8', borderRadius: '8px' }}
          >
            <Flex $direction="column" $css={{ flex: '3 1 0' }}>
              <SongInfoContainer>
                <Thumbnail src={albumCoverUrl} size="md" />
                <Info>
                  <SongTitle aria-label={`노래 ${title}`}>{title}</SongTitle>
                  <Singer aria-label={`가수 ${singer}`}>{singer}</Singer>
                </Info>
              </SongInfoContainer>
              <Spacing direction="vertical" size={16} />
              <Youtube videoId={songVideoId} />
            </Flex>
            <TimerProvider time={15}>
              <KillingPartInterface killingParts={killingParts} songId={id} />
            </TimerProvider>
          </Flex>
        </VideoPlayerProvider>
      </Container>
    );
  }
);

SongDetailItem.displayName = 'SongDetailItem';

export default SongDetailItem;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
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
  font-size: 20px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.lg}) {
    width: 220px;
    font-size: 18px;
  }
  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    font-size: 16px;
    overflow: hidden;

    text-overflow: ellipsis;
    white-space: nowrap;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    font-size: 16px;
  }
`;

const Singer = styled.div`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.lg}) {
    font-size: 16px;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    font-size: 14px;
  }
`;
