import { styled } from 'styled-components';
import emptyHeartIcon from '@/assets/icon/empty-heart.svg';
import emptyPlayIcon from '@/assets/icon/empty-play.svg';
import fillPlayIcon from '@/assets/icon/fill-play.svg';
import shareIcon from '@/assets/icon/share.svg';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { getPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import formatOrdinals from '@/shared/utils/formatOrdinals';
import type { KillingPart } from '@/shared/types/song';
import type React from 'react';

interface KillingPartTrackProps {
  killingPart: KillingPart;
  nowPlayingTrack: number;
  changePlayingTrack: React.ChangeEventHandler<HTMLInputElement>;
}

const KillingPartTrack = ({
  killingPart: { rank, start, end, likeCount, partVideoUrl },
  nowPlayingTrack,
  changePlayingTrack,
}: KillingPartTrackProps) => {
  const { showToast } = useToastContext();
  const { videoPlayer } = useVideoPlayerContext();

  const ordinalRank = formatOrdinals(rank);
  const playingTime = getPlayingTimeText(start, end);
  const isPlaying = nowPlayingTrack === rank;
  const partLength = end - start;

  const playIcon = isPlaying ? fillPlayIcon : emptyPlayIcon;
  const currentPlayTime = videoPlayer?.getCurrentTime();

  const copyKillingPartUrl = async () => {
    await copyClipboard(partVideoUrl);
    showToast('클립보드에 영상 링크가 복사되었습니다.');
  };

  // TODO 비디오 프로바이더로 분리
  // const playKillingPart = () => {
  //   videoPlayer?.seekTo(start, false);
  //   videoPlayer?.playVideo();
  // };

  // const stopPlaying = () => {
  //   const isPlaying = videoPlayer?.getPlayerState() === 1;

  //   if (isPlaying) {
  //     videoPlayer.pauseVideo();
  //   }
  // };

  return (
    <Container htmlFor={`play-${rank}`} tabIndex={0} aria-label={`${rank}등 킬링파트 재생하기`}>
      <FLexContainer>
        <Rank>{ordinalRank}</Rank>
        <PlayButton
          id={`play-${rank}`}
          name="track"
          type="radio"
          value={rank}
          onChange={changePlayingTrack}
          checked={isPlaying}
        />
        <ButtonIcon src={playIcon} alt="" />
        <PlayingTime>{playingTime}</PlayingTime>
      </FLexContainer>
      <FLexContainer>
        <LikeButton aria-label={`${rank}등 킬링파트 좋아요 하기`}>
          <ButtonIcon src={emptyHeartIcon} alt="" />
          <ButtonTitle>{`${likeCount} Likes`}</ButtonTitle>
        </LikeButton>
        <ShareButton
          aria-label={`${rank}등 킬링파트 유튜브 링크 공유하기`}
          onClick={copyKillingPartUrl}
        >
          <ButtonIcon src={shareIcon} alt="" />
          <ButtonTitle>Share</ButtonTitle>
        </ShareButton>
      </FLexContainer>
      {isPlaying && <ProgressBar value={currentPlayTime} max={partLength} aria-hidden="true" />}
    </Container>
  );
};

export default KillingPartTrack;

export const Container = styled.label`
  position: relative;
  display: flex;
  padding: 0 12px;
  align-items: center;
  justify-content: space-between;
  gap: 12px;
  width: 100%;
  height: 60px;
  background-color: ${({ theme: { color } }) => color.secondary};
  border-radius: 4px;
  color: ${({ theme: { color } }) => color.white};
  cursor: pointer;

  &:has(input:checked) {
    background-color: ${({ theme: { color } }) => color.disabledBackground};
  }
`;

export const Rank = styled.div`
  width: 36px;
  text-align: center;
  font-size: 16px;
  font-style: normal;
  font-weight: 700;
`;

export const PlayButton = styled.input`
  display: none;

  &:checked {
    background: no-repeat url(${emptyPlayIcon}) center;
  }
`;

export const PlayingTime = styled.span`
  width: 120px;
  text-align: center;
  font-size: 16px;
  font-weight: 700;
`;

export const PartLength = styled.span`
  width: 26px;
  text-align: center;
  font-size: 14px;
  font-weight: 700;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    display: none;
  }
`;

export const LikeCount = styled.span`
  width: 50px;
  color: #d8d8d8;
  text-align: right;
  font-size: 12px;
`;

export const LikeButton = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  gap: 2px;
`;

export const ShareButton = styled.button`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: flex-end;
  gap: 2px;
`;

export const ButtonTitle = styled.span`
  font-size: 8px;
`;

export const ButtonIcon = styled.img`
  width: 22px;
  height: 22px;
`;

export const ProgressBar = styled.progress`
  position: absolute;
  bottom: 0;
  left: 0;
  width: 100%;
  height: 2px;
  appearance: none;

  &::-webkit-progress-bar {
    background-color: ${({ theme: { color } }) => color.white};
  }

  &::-webkit-progress-value {
    background-color: #ff137f;
  }
`;

export const FLexContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;
