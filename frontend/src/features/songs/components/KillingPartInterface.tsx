import { useEffect, useRef, useState } from 'react';
import { Flex } from 'shook-layout';
import { styled } from 'styled-components';
// NOTE: 댓글 기능 임시 중단
// import CommentList from '@/features/comments/components/CommentList';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Spacing from '@/shared/components/Spacing';
import useTimerContext from '@/shared/components/Timer/hooks/useTimerContext';
import ToggleSwitch from '@/shared/components/ToggleSwitch/ToggleSwitch';
import KillingPartTrackList from './KillingPartTrackList';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartInterfaceProps {
  killingParts: SongDetail['killingParts'];
  memberPart: SongDetail['memberPart'];
  songId: number;
}

const DEFAULT_PART_ID = -1;

const KillingPartInterface = ({ killingParts, songId, memberPart }: KillingPartInterfaceProps) => {
  const [nowPlayingTrack, setNowPlayingTrack] = useState(DEFAULT_PART_ID);
  // NOTE: 댓글 기능 임시 중단
  // eslint-disable-next-line @typescript-eslint/no-unused-vars
  const [commentsPartId, setCommentsPartId] = useState<KillingPart['id']>(DEFAULT_PART_ID);
  const [isRepeat, setIsRepeat] = useState(false);
  const { videoPlayer, playerState, seekTo, pause } = useVideoPlayerContext();
  const { countedTime, startTimer, resetTimer, pauseTimer } = useTimerContext();
  const observingTargetRef = useRef<HTMLDivElement>(null);

  const toggleRepetition = () => {
    setIsRepeat((prev) => !prev);
  };

  useEffect(() => {
    if (document.activeElement === videoPlayer.current?.getIframe()) {
      setNowPlayingTrack(DEFAULT_PART_ID);
    }
  }, [videoPlayer, playerState]);

  const trackList = [...killingParts, memberPart].map((part, i) => ({ part, order: i + 1 }));

  useEffect(() => {
    if (nowPlayingTrack === DEFAULT_PART_ID) return;

    const track = trackList.find(({ order }) => order === nowPlayingTrack);

    if (!track || !videoPlayer.current) return;

    const { part } = track;
    const partLength = (part.end - part.start) * 1000;
    const remainingTime = partLength - countedTime * 1000;

    let timeoutId1: number;
    let timeoutId2: number;
    let intervalIds: number;

    if (isRepeat) {
      timeoutId1 = window.setTimeout(() => {
        resetTimer();
        seekTo(part.start);

        intervalIds = window.setInterval(() => {
          resetTimer();
          seekTo(part.start);
        }, partLength);
      }, remainingTime);
    } else {
      timeoutId2 = window.setTimeout(() => {
        resetTimer();
        pause();
        setNowPlayingTrack(DEFAULT_PART_ID);
      }, remainingTime);
    }

    return () => {
      window.clearTimeout(timeoutId1);
      window.clearTimeout(timeoutId2);
      window.clearInterval(intervalIds);
    };
  }, [trackList, isRepeat, nowPlayingTrack, videoPlayer, pause, resetTimer, seekTo, countedTime]);

  useEffect(() => {
    resetTimer();
  }, [nowPlayingTrack, resetTimer]);

  useEffect(() => {
    if (playerState === null) return;

    if (playerState === YT.PlayerState.BUFFERING) {
      const bufferingTimer = window.setTimeout(pauseTimer, 300);

      return () => {
        window.clearTimeout(bufferingTimer);
        startTimer();
      };
    }

    if (playerState === YT.PlayerState.PLAYING) {
      startTimer();
    }
  }, [playerState, pauseTimer, resetTimer, startTimer]);

  useEffect(() => {
    const onObserveTarget: IntersectionObserverCallback = async ([entry], observer) => {
      if (entry.intersectionRatio > 0) return;

      observer.unobserve(entry.target);
      setNowPlayingTrack(DEFAULT_PART_ID);
      pause();
      observer.observe(entry.target);
    };

    const observer = new IntersectionObserver(onObserveTarget, {
      threshold: [0],
    });

    if (!observingTargetRef.current) return;
    observer.observe(observingTargetRef.current);

    return () => observer.disconnect();
  }, [pause]);

  return (
    <Flex $direction="column">
      <FlexContainer ref={observingTargetRef}>
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
      <KillingPartTrackList
        killingParts={killingParts}
        memberPart={memberPart}
        songId={songId}
        nowPlayingTrack={nowPlayingTrack}
        setNowPlayingTrack={setNowPlayingTrack}
        setCommentsPartId={setCommentsPartId}
      />
      {/* NOTE: 댓글 기능 임시 중단 */}
      {/* {commentsPartId !== DEFAULT_PART_ID && (
        <CommentList songId={songId} partId={commentsPartId} />
      )} */}
    </Flex>
  );
};

export default KillingPartInterface;

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
  height: 60px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    height: auto;
  }
`;
