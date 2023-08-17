import { useEffect, useState } from 'react';
import { styled } from 'styled-components';
import CommentList from '@/features/comments/components/CommentList';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Spacing from '@/shared/components/Spacing';
import useTimerContext from '@/shared/components/Timer/hooks/useTimerContext';
import ToggleSwitch from '@/shared/components/ToggleSwitch/ToggleSwitch';
import KillingPartTrackList from './KillingPartTrackList';
import type { KillingPart, SongDetail } from '@/shared/types/song';

interface KillingPartInterfaceProps {
  killingParts: SongDetail['killingParts'];
  songId: number;
}

const DEFAULT_PART_ID = -1;

const KillingPartInterface = ({ killingParts, songId }: KillingPartInterfaceProps) => {
  const [nowPlayingTrack, setNowPlayingTrack] = useState<KillingPart['id']>(DEFAULT_PART_ID);
  const [commentsPartId, setCommentsPartId] = useState<KillingPart['id']>(DEFAULT_PART_ID);
  const [isRepeat, setIsRepeat] = useState(false);
  const { videoPlayer, playerState, seekTo, pause } = useVideoPlayerContext();
  const { countedTime, startTimer, resetTimer, pauseTimer } = useTimerContext();

  const toggleRepetition = () => {
    setIsRepeat((prev) => !prev);
  };

  useEffect(() => {
    if (document.activeElement === videoPlayer.current?.getIframe()) {
      setNowPlayingTrack(DEFAULT_PART_ID);
    }
  }, [videoPlayer, playerState]);

  useEffect(() => {
    const part = killingParts.find((part) => part.id === nowPlayingTrack);
    if (!part || !videoPlayer.current) return;

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
  }, [
    killingParts,
    isRepeat,
    nowPlayingTrack,
    videoPlayer,
    pause,
    resetTimer,
    seekTo,
    countedTime,
  ]);

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

  return (
    <>
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
      <KillingPartTrackList
        killingParts={killingParts}
        nowPlayingTrack={nowPlayingTrack}
        setNowPlayingTrack={setNowPlayingTrack}
        setCommentsPartId={setCommentsPartId}
      />
      {commentsPartId !== DEFAULT_PART_ID && (
        <CommentList songId={songId.toString()} partId={commentsPartId} />
      )}
    </>
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
`;
