import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import CommentList from '@/features/comments/components/CommentList';
import KillingPartInfo from '@/features/songs/components/KillingPartInfo';
import Thumbnail from '@/features/songs/components/Thumbnail';
import { useGetSongDetail } from '@/features/songs/remotes/useGetSongDetail';
import Youtube from '@/features/youtube/components/Youtube';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRAlert from '@/shared/components/SRAlert';
import SRHeading from '@/shared/components/SRHeading';
import ToggleGroup from '@/shared/components/ToggleGroup/ToggleGroup';
import ToggleSwitch from '@/shared/components/ToggleSwitch/ToggleSwitch';

const SongDetailPage = () => {
  const { id = '' } = useParams();
  const [isRepeat, setIsRepeat] = useState(true);
  const [killingRank, setKillingRank] = useState<number | null>(null);
  const { songDetail } = useGetSongDetail(Number(id));
  const timer = useRef<number>(-1);
  const { videoPlayer } = useVideoPlayerContext();

  useEffect(() => {
    if (!videoPlayer?.seekTo) return;
    if (!songDetail) return;

    const part = songDetail.killingParts?.find((part) => part.rank === killingRank);

    if (!part) {
      videoPlayer?.seekTo(0, true);
      videoPlayer?.playVideo();
      return;
    } else {
      videoPlayer?.seekTo(part.start, true);
    }

    videoPlayer?.playVideo();

    if (isRepeat) {
      timer.current = window.setInterval(
        () => {
          videoPlayer?.seekTo(part.start, true);
        },
        (part.end - part.start) * 1000
      );
    }

    return () => {
      window.clearInterval(timer.current);
    };
  }, [isRepeat, killingRank, videoPlayer, songDetail]);

  if (!songDetail) return;
  const { killingParts, singer, title, songVideoUrl, albumCoverUrl } = songDetail;
  const killingPart = killingParts?.find((part) => part.rank === killingRank);

  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  const changeKillingRank = (rank: number) => {
    setKillingRank(rank);
  };

  const toggleRepetition = () => {
    setIsRepeat(!isRepeat);
  };

  return (
    <Wrapper>
      <SRHeading>킬링파트 듣기 페이지</SRHeading>
      <BigTitle aria-label="킬링파트 듣기">킬링파트 듣기 🎧</BigTitle>
      <Spacing direction="vertical" size={20} />
      <SongInfoContainer>
        <Thumbnail src={albumCoverUrl} />
        <Info>
          <SongTitle aria-label={`노래 ${title}`}>{title}</SongTitle>
          <Singer aria-label={`가수 ${singer}`}>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <Spacing direction="vertical" size={20} />
      <Youtube videoId={videoId} />
      <Spacing direction="vertical" size={16} />
      <RegisterTitle aria-label="인기 많은 킬링파트를 들어보세요">
        인기 많은 킬링파트를 들어보세요 🎧
      </RegisterTitle>
      <Spacing direction="vertical" size={16} />
      <ToggleGroup onChangeButton={changeKillingRank}>
        <ToggleGroup.Button tabIndex={0} index={1} aria-label="1등 킬링파트 노래 듣기">
          1st
        </ToggleGroup.Button>
        <Spacing direction="horizontal" size={10} />
        <ToggleGroup.Button tabIndex={0} index={2} aria-label="2등 킬링파트 노래 듣기">
          2nd
        </ToggleGroup.Button>
        <Spacing direction="horizontal" size={10} />
        <ToggleGroup.Button index={3} aria-label="3등 킬링파트 노래 듣기">
          3rd
        </ToggleGroup.Button>
        <Spacing direction="horizontal" size={10} />
        <ToggleGroup.Button index={4} aria-label="노래 전체 듣기">
          전체
        </ToggleGroup.Button>
      </ToggleGroup>
      <Spacing direction="vertical" size={10} />
      <SwitchWrapper>
        <SwitchLabel htmlFor="repetition">반복재생</SwitchLabel>
        <ToggleSwitch
          id="repetition"
          on={toggleRepetition}
          off={toggleRepetition}
          defaultToggle={isRepeat}
        />
      </SwitchWrapper>
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
  display: flex;
  align-items: center;
  gap: 12px;
`;

const Info = styled.div``;

const SongTitle = styled.p`
  font-size: 24px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

const Singer = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;

const SwitchWrapper = styled.div`
  display: flex;
  justify-content: end;
  margin: 0 8px;
  column-gap: 8px;
`;

const SwitchLabel = styled.label`
  color: ${({ theme: { color } }) => color.white};
  font-size: 14px;
`;

const RegisterTitle = styled.p`
  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;
