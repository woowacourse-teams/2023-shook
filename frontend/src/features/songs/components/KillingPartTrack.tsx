import { css, styled } from 'styled-components';
import emptyHeartIcon from '@/assets/icon/empty-heart.svg';
import emptyPlayIcon from '@/assets/icon/empty-play.svg';
import fillPlayIcon from '@/assets/icon/fill-play.svg';
import shareIcon from '@/assets/icon/share.svg';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { toPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import formatOrdinals from '@/shared/utils/formatOrdinals';
import type { KillingPart } from '@/shared/types/song';
import type React from 'react';

interface KillingPartTrackProps {
  killingPart: KillingPart;
  isPlaying: boolean;
  changePlayingTrack: React.ChangeEventHandler<HTMLInputElement>;
}

const KillingPartTrack = ({
  killingPart: { rank, start, end, likeCount, partVideoUrl },
  isPlaying,
  changePlayingTrack,
}: KillingPartTrackProps) => {
  const { showToast } = useToastContext();
  const { videoPlayer } = useVideoPlayerContext();

  const ordinalRank = formatOrdinals(rank);
  const playingTime = toPlayingTimeText(start, end);
  const partLength = end - start;

  const playIcon = isPlaying ? fillPlayIcon : emptyPlayIcon;
  const currentPlayTime = videoPlayer.current?.getCurrentTime();

  const copyKillingPartUrl = async () => {
    await copyClipboard(partVideoUrl);
    showToast('영상 링크가 복사되었습니다.');
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
    <Container
      $isPlaying={isPlaying}
      htmlFor={`play-${rank}`}
      tabIndex={0}
      aria-label={`${rank}등 킬링파트 재생하기`}
    >
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
      <ButtonContainer>
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
      </ButtonContainer>
      {isPlaying && <ProgressBar value={currentPlayTime} max={partLength} aria-hidden="true" />}
    </Container>
  );
};

export default KillingPartTrack;

const Container = styled.label<{ $isPlaying: boolean }>`
  cursor: pointer;

  position: relative;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 60px;
  padding: 0 12px;

  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color }, $isPlaying }) => {
    return $isPlaying ? color.disabledBackground : color.secondary;
  }};
  border-radius: 4px;
`;

const Rank = styled.span`
  width: 36px;
  font-size: 16px;
  font-weight: 700;
  text-align: center;
`;

const PlayButton = styled.input`
  display: none;
`;

const PlayingTime = styled.span`
  width: 120px;
  font-size: 16px;
  font-weight: 700;
  text-align: center;
`;

const ButtonWithIcon = css`
  display: flex;
  flex-direction: column;
  gap: 2px;
  align-items: center;

  width: 40px;
`;

const LikeButton = styled.button`
  ${ButtonWithIcon}
`;

const ShareButton = styled.button`
  ${ButtonWithIcon}
`;

const ButtonTitle = styled.span`
  font-size: 8px;
`;

const ButtonIcon = styled.img`
  width: 22px;
  height: 22px;
`;

const ProgressBar = styled.progress`
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

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
`;

const FLexContainer = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;
