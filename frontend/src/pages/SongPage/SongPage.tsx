import { useEffect, useRef, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { styled } from 'styled-components';
import { Spacing } from '@/components/@common';
import { ToggleGroup } from '@/components/@common/ToggleGroup';
import { ToggleSwitch } from '@/components/@common/ToggleSwitch';
import CommentList from '@/components/CommentList/CommentList';
import { KillingPartInfo } from '@/components/KillingPartInfo';
import Thumbnail from '@/components/PopularSongItem/Thumbnail';
import { RegisterTitle } from '@/components/VoteInterface/VoteInterface.style';
import { useVideoPlayerContext } from '@/components/Youtube';
import Youtube from '@/components/Youtube/Youtube';
import { useGetSongDetail } from '@/hooks/song';
import {
  Info,
  Wrapper,
  Singer,
  PrimarySpan,
  SongInfoContainer,
  SongTitle,
  SubTitle,
  SwitchLabel,
  SwitchWrapper,
} from './SongPage.style';

const SongPage = () => {
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
      <BigTitle>킬링파트 듣기 🎧</BigTitle>
      <Spacing direction="vertical" size={20} />
      <SongInfoContainer>
        <Thumbnail src={albumCoverUrl} alt={`${title} 앨범 자켓`} />
        <Info>
          <SongTitle>{title}</SongTitle>
          <Singer>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <Spacing direction="vertical" size={20} />
      <Youtube videoId={videoId} />
      <Spacing direction="vertical" size={20} />
      <SubTitle>
        <UnderLine>
          <PrimarySpan>킬링파트</PrimarySpan> 듣기
        </UnderLine>
        <Link to={`/${id}`}>
          <PrimarySpan>킬링파트</PrimarySpan> 투표
        </Link>
      </SubTitle>
      <Spacing direction="vertical" size={16} />
      <RegisterTitle>인기 많은 킬링파트를 들어보세요 🎧</RegisterTitle>
      <Spacing direction="vertical" size={16} />
      <ToggleGroup onChangeButton={changeKillingRank}>
        <ToggleGroup.button index={1}>1st</ToggleGroup.button>
        <Spacing direction="horizontal" size={10} />
        <ToggleGroup.button index={2}>2nd</ToggleGroup.button>
        <Spacing direction="horizontal" size={10} />
        <ToggleGroup.button index={3}>3rd</ToggleGroup.button>
        <Spacing direction="horizontal" size={10} />
        <ToggleGroup.button index={4}>전체</ToggleGroup.button>
      </ToggleGroup>
      <Spacing direction="vertical" size={10} />
      <SwitchWrapper>
        <SwitchLabel>반복재생</SwitchLabel>
        <ToggleSwitch on={toggleRepetition} off={toggleRepetition} defaultToggle={isRepeat} />
      </SwitchWrapper>
      <Spacing direction="vertical" size={10} />
      <KillingPartInfo killingPart={killingPart} />
      <Spacing direction="vertical" size={10} />
      {killingPart && <CommentList songId={id} partId={killingParts[killingRank! - 1].id} />}
    </Wrapper>
  );
};

export default SongPage;

export const UnderLine = styled.div`
  border-bottom: 2px solid white;
`;

export const BigTitle = styled.h2`
  font-size: 28px;
  font-weight: 700;
`;
