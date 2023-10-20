import { useCallback } from 'react';
import { css, styled } from 'styled-components';
import emptyPlayIcon from '@/assets/icon/empty-play.svg';
import fillPlayIcon from '@/assets/icon/fill-play.svg';
import shareIcon from '@/assets/icon/share.svg';
import trashIcon from '@/assets/icon/trash.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import LoginModal from '@/features/auth/components/LoginModal';
import { deleteMemberParts } from '@/features/member/remotes/memberParts';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useModal from '@/shared/components/Modal/hooks/useModal';
import useTimerContext from '@/shared/components/Timer/hooks/useTimerContext';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { GA_ACTIONS, GA_CATEGORIES } from '@/shared/constants/GAEventName';
import sendGAEvent from '@/shared/googleAnalytics/sendGAEvent';
import { useMutation } from '@/shared/hooks/useMutation';
import { toPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import formatOrdinals from '@/shared/utils/formatOrdinals';
import useKillingPartLikes from '../hooks/useKillingPartLikes';
import MyPartModal from './MyPartModal';
import type { KillingPart } from '@/shared/types/song';
import type React from 'react';

interface KillingPartTrackProps {
  order: number;
  killingPart: KillingPart;
  songId: number;
  isNowPlayingTrack: boolean;
  setNowPlayingTrack: React.Dispatch<React.SetStateAction<number>>;
  setCommentsPartId: React.Dispatch<React.SetStateAction<KillingPart['id']>>;
  isMyKillingPart?: boolean;
  hideMyPart?: () => void;
}

const KillingPartTrack = ({
  order,
  killingPart: { id: partId, rank, start, end, likeCount, likeStatus },
  songId,
  isNowPlayingTrack,
  setNowPlayingTrack,
  setCommentsPartId,
  isMyKillingPart,
  hideMyPart,
}: KillingPartTrackProps) => {
  const { showToast } = useToastContext();
  const { seekTo, pause, playerState, videoPlayer } = useVideoPlayerContext();
  const { calculatedLikeCount, heartIcon, toggleKillingPartLikes } = useKillingPartLikes({
    likeCount,
    likeStatus,
    songId,
    partId,
  });
  const { countedTime: currentPlayTime } = useTimerContext();
  const {
    isOpen: isLoginModalOpen,
    closeModal: closeLoginModal,
    openModal: openLoginModal,
  } = useModal();
  const {
    isOpen: isMyPartModal,
    closeModal: closeMyPartModal,
    openModal: openMyPartModal,
  } = useModal();
  const { user } = useAuthContext();
  const isLoggedIn = user !== null;

  const ordinalRank = formatOrdinals(rank);
  const playingTime = toPlayingTimeText(start, end);
  const partLength = end - start;

  const copyKillingPartUrl = async () => {
    sendGAEvent({
      action: GA_ACTIONS.COPY_URL,
      category: GA_CATEGORIES.SONG_DETAIL,
      memberId: user?.memberId,
    });

    await copyClipboard(window.location.href);
    showToast('영상 링크가 복사되었습니다.');
  };

  const copyMyPartUrl = async () => {
    sendGAEvent({
      action: GA_ACTIONS.COPY_URL,
      category: GA_CATEGORIES.SONG_DETAIL,
      memberId: user?.memberId,
    });

    await copyClipboard(`${videoPlayer.current?.getVideoUrl()}&t=${start}s`);
    showToast('영상 링크가 복사되었습니다.');
  };

  const getPlayIcon = useCallback(() => {
    if (!isNowPlayingTrack || playerState === YT.PlayerState.PAUSED) {
      return emptyPlayIcon;
    }

    if (
      playerState === null ||
      playerState === YT.PlayerState.UNSTARTED ||
      playerState === YT.PlayerState.PLAYING ||
      playerState === YT.PlayerState.BUFFERING
    ) {
      return fillPlayIcon;
    }
  }, [playerState, isNowPlayingTrack]);

  const playTrack = () => {
    seekTo(start);
    setNowPlayingTrack(order);

    if (order !== 4) {
      setCommentsPartId(partId);
    } else {
      setCommentsPartId(-1);
    }
  };

  const stopTrack = () => {
    pause();
    setNowPlayingTrack(-1);
  };

  const toggleTrackPlayAndStop = () => {
    sendGAEvent({
      action: GA_ACTIONS.PLAY,
      category: GA_CATEGORIES.SONG_DETAIL,
      memberId: user?.memberId,
    });

    if (isNowPlayingTrack) {
      stopTrack();
    } else {
      playTrack();
    }
  };

  const toggleLike = () => {
    sendGAEvent({
      action: GA_ACTIONS.LIKE,
      category: GA_CATEGORIES.SONG_DETAIL,
      memberId: user?.memberId,
    });

    toggleKillingPartLikes();
  };

  const { mutateData: deleteMemberPart } = useMutation(() => deleteMemberParts(partId));

  const deleteMyPart = async () => {
    if (!hideMyPart) return;

    await deleteMemberPart();

    hideMyPart();
    pause();
    closeMyPartModal();
    showToast('내 파트가 삭제되었습니다.');
  };

  return (
    <Container
      $isNowPlayingTrack={isNowPlayingTrack}
      htmlFor={`play-${songId}-${order}`}
      tabIndex={0}
      role="radio"
      aria-label={isMyKillingPart ? '나의 킬링파트 재생하기' : `${rank}등 킬링파트 재생하기`}
    >
      <FLexContainer>
        <Rank>{isMyKillingPart ? 'MY' : ordinalRank}</Rank>
        <PlayButton
          id={`play-${songId}-${order}`}
          name="track"
          type="radio"
          onChange={toggleTrackPlayAndStop}
          onClick={toggleTrackPlayAndStop}
          checked={isNowPlayingTrack}
        />
        <ButtonIcon src={getPlayIcon()} alt="" />
        <PlayingTime>{playingTime}</PlayingTime>
      </FLexContainer>
      <ButtonContainer>
        {isMyKillingPart ? (
          <>
            <DeleteButton
              type="button"
              onClick={openMyPartModal}
              aria-label="나의 킬링파트 삭제하기"
            >
              <ButtonIcon src={trashIcon} alt="" />
            </DeleteButton>
            <ShareButton aria-label={'나의 킬링파트 유튜브 링크 공유하기'} onClick={copyMyPartUrl}>
              <ButtonIcon src={shareIcon} alt="" />
            </ShareButton>
          </>
        ) : (
          <>
            <LikeButton
              onClick={isLoggedIn ? toggleLike : openLoginModal}
              aria-label={`${rank}등 킬링파트 좋아요 하기`}
            >
              <ButtonIcon src={heartIcon} alt="" />
            </LikeButton>
            <ShareButton
              aria-label={`${rank}등 킬링파트 링크 공유하기`}
              onClick={copyKillingPartUrl}
            >
              <ButtonIcon src={shareIcon} alt="" />
            </ShareButton>
          </>
        )}
      </ButtonContainer>
      {isNowPlayingTrack && (
        <ProgressBar value={currentPlayTime} max={partLength} aria-hidden="true" />
      )}

      <MyPartModal isOpen={isMyPartModal} closeModal={closeMyPartModal} onDelete={deleteMyPart} />

      <LoginModal
        isOpen={isLoginModalOpen}
        message={
          '로그인하여 킬링파트에 "좋아요!"를 눌러주세요!\n"좋아요!"한 노래는 마이페이지에 저장됩니다!'
        }
        closeModal={closeLoginModal}
      />
    </Container>
  );
};

export default KillingPartTrack;

const Container = styled.label<{ $isNowPlayingTrack: boolean }>`
  cursor: pointer;

  position: relative;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: 60px;
  padding: 0 12px;

  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color }, $isNowPlayingTrack }) => {
    return $isNowPlayingTrack ? color.disabledBackground : color.secondary;
  }};
  border-radius: ${({ $isNowPlayingTrack }) => {
    return $isNowPlayingTrack ? '4px 4px 0px 0px' : '4px';
  }};

  transition: all 0.3s ease;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    height: 46px;
  }
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
  justify-content: center;

  width: 38px;
  height: 100%;
`;

const DeleteButton = styled.button`
  ${ButtonWithIcon}
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

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    width: 20px;
    height: 20px;
  }
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
    transition: all 0.1s ease;
  }
`;

const ButtonContainer = styled.div`
  display: flex;
  align-items: center;
  height: 100%;
`;

const FLexContainer = styled.div`
  display: flex;
  gap: 12px;
  align-items: center;
`;
