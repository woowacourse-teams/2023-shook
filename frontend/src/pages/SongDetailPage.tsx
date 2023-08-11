import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CommentList from '@/features/comments/components/CommentList';
import KillingPartInfo from '@/features/songs/components/KillingPartInfo';
import KillingPartTrackList from '@/features/songs/components/KillingPartTrackList';
import Thumbnail from '@/features/songs/components/Thumbnail';
import { useGetSongDetail } from '@/features/songs/remotes/useGetSongDetail';
import Youtube from '@/features/youtube/components/Youtube';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRAlert from '@/shared/components/SRAlert';
import SRHeading from '@/shared/components/SRHeading';
import ToggleSwitch from '@/shared/components/ToggleSwitch/ToggleSwitch';

const SongDetailPage = () => {
  const { id = '' } = useParams();
  const [isRepeat, setIsRepeat] = useState(true);
  const [killingRank, setKillingRank] = useState<number | null>(null);
  const { songDetail } = useGetSongDetail(Number(id));
  const timer = useRef<number>(-1);
  const { videoPlayer } = useVideoPlayerContext();

  useEffect(() => {
    if (!videoPlayer.current?.seekTo) return;
    if (!songDetail) return;

    const part = songDetail.killingParts?.find((part) => part.rank === killingRank);

    if (!part) {
      videoPlayer.current?.seekTo(0, true);
      videoPlayer.current?.playVideo();
      return;
    } else {
      videoPlayer.current?.seekTo(part.start, true);
    }

    videoPlayer.current?.playVideo();

    if (isRepeat) {
      timer.current = window.setInterval(
        () => {
          videoPlayer.current?.seekTo(part.start, true);
        },
        (part.end - part.start) * 1000
      );
    }

    return () => {
      window.clearInterval(timer.current);
    };
  }, [isRepeat, killingRank, videoPlayer.current, songDetail]);

  if (!songDetail) return;
  const { killingParts, singer, title, songVideoUrl, albumCoverUrl } = songDetail;
  const killingPart = killingParts?.find((part) => part.rank === killingRank);

  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  const toggleRepetition = () => {
    setIsRepeat(!isRepeat);
  };

  return (
    <Wrapper>
      <SRHeading>킬링파트 듣기 페이지</SRHeading>
      <BigTitle aria-label="킬링파트 듣기">킬링파트 듣기</BigTitle>
      <Spacing direction="vertical" size={20} />
      <SongInfoContainer>
        <Thumbnail src={albumCoverUrl} size="md" />
        <Info>
          <SongTitle aria-label={`노래 ${title}`}>{title}</SongTitle>
          <Singer aria-label={`가수 ${singer}`}>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <Spacing direction="vertical" size={20} />
      <Youtube videoId={videoId} />
      <Spacing direction="vertical" size={16} />
      <FlexContainer>
        <TitleWrapper aria-label="Top 3 킬링파트 듣기">
          <ItalicTitle aria-hidden="true">Top 3</ItalicTitle>
          <NormalTitle aria-hidden="true"> Killing part</NormalTitle>
        </TitleWrapper>
        <SwitchWrapper>
          <SwitchLabel htmlFor="repetition">구간 반복</SwitchLabel>
          <ToggleSwitch
            id="repetition"
            on={toggleRepetition}
            off={toggleRepetition}
            defaultToggle={isRepeat}
          />
        </SwitchWrapper>
      </FlexContainer>
      <Spacing direction="vertical" size={16} />
      <KillingPartTrackList killingParts={killingParts} />
      <Spacing direction="vertical" size={10} />
      <Spacing direction="vertical" size={10} />
      <KillingPartInfo killingPart={killingPart} />
      <Spacing direction="vertical" size={10} />
      {killingPart && <CommentList songId={id} partId={killingParts[killingRank! - 1].id} />}
      <SRAlert>{`${killingRank === 4 ? '전체' : `${killingRank}등 킬링파트`} 재생`}</SRAlert>
    </Wrapper>
  );
};

export default SongDetailPage;

const BigTitle = styled.h2`
  font-size: 28px;
  font-weight: 700;
`;

const Wrapper = styled(Flex)`
  flex-direction: column;
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

const SwitchWrapper = styled.div`
  display: flex;
  column-gap: 8px;
`;

const SwitchLabel = styled.label`
  font-size: 12px;
  color: ${({ theme: { color } }) => color.white};
`;

const TitleWrapper = styled.div`
  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;

const ItalicTitle = styled.span`
  font-style: italic;
  color: ${({ theme: { color } }) => color.primary};
`;

const NormalTitle = styled.span`
  color: ${({ theme: { color } }) => color.white};
`;

const FlexContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
`;
