import styled, { css, keyframes } from 'styled-components';
import playIcon from '@/assets/icon/fill-play.svg';
import pauseIcon from '@/assets/icon/pause.svg';
import pinIcon from '@/assets/icon/pin.svg';
import playStreamIcon from '@/assets/icon/play-stream.svg';
import removeIcon from '@/assets/icon/remove.svg';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import usePin from '@/features/killingParts/hooks/usePin';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';
import { toMinSecText } from '@/shared/utils/convertTime';

const VideoBadges = () => {
  const { partStartTime, isPlayingEntire, scrollingRef, toggleEntirePlaying } =
    useCollectingPartContext();
  const {
    pinList,
    isPinListEmpty,
    activePinIndex,
    pinContainerRef,
    pinAnimationRef,
    addPin,
    deletePin,
    playPin,
  } = usePin();
  const video = useVideoPlayerContext();

  const partStartTimeText = toMinSecText(partStartTime);
  const isPaused = video.playerState === YT.PlayerState.PAUSED;
  const videoPlay = () => {
    if (isPlayingEntire) {
      video.play();
    } else {
      video.seekTo(partStartTime);
    }
  };

  const videoPause = () => {
    if (scrollingRef.current === null) {
      video.pause();
    }
  };

  return (
    <>
      <Flex $gap={14} $justify="flex-end">
        <StartBadge>
          <img src={playStreamIcon} style={{ marginRight: '4px' }} alt="" />
          {partStartTimeText}
        </StartBadge>
        <Badge as="button" onClick={addPin} $isActive={!isPinListEmpty}>
          <img src={pinIcon} alt="나만의 파트 임시 저장" />
        </Badge>
        <Badge as="button" type="button" onClick={isPaused ? videoPlay : videoPause}>
          <img src={isPaused ? playIcon : pauseIcon} alt={'재생 혹은 정지'} />
        </Badge>
        <Badge as="button" type="button" $isActive={isPlayingEntire} onClick={toggleEntirePlaying}>
          전체 듣기
        </Badge>
      </Flex>
      <PinFlex $gap={4} ref={pinContainerRef}>
        {!isPinListEmpty && (
          <DeleteBadge as="button" onClick={deletePin}>
            <img src={removeIcon} alt="나만의 파트 임시 저장 삭제하기" />
          </DeleteBadge>
        )}
        <PinInner $gap={4} ref={pinContainerRef}>
          {pinList.map((pin, index) => (
            <PinBadge
              key={pin.text + pinAnimationRef.current}
              as="button"
              onClick={playPin(pin.partStartTime, pin.interval)}
              $isActive={index === activePinIndex}
              $isNew={index === 0}
            >
              {pin.text}
            </PinBadge>
          ))}
        </PinInner>
      </PinFlex>
    </>
  );
};
export default VideoBadges;

const PinFlex = styled(Flex)`
  width: 100%;
`;

const PinInner = styled(Flex)`
  overflow-x: scroll;
  width: calc(100% - 44px);
`;

const Badge = styled.span<{ $isActive?: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  min-width: 40px;
  height: 30px;
  padding: 0 10px;

  font-size: 14px;
  color: ${({ theme: { color } }) => color.white};
  text-align: center;

  background-color: ${({ theme: { color }, $isActive }) =>
    $isActive ? color.magenta700 : color.disabled};
  border-radius: 40px;

  transition:
    background-color 0.2s ease-in,
    box-shadow 0.2s ease;

  &:active {
    box-shadow: 0 0 0 1px inset white;
  }

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;

const StartBadge = styled(Badge)`
  margin-right: auto;
  letter-spacing: 1px;
`;

const slideFirstItem = keyframes`
  from {
    opacity: 0;
    transform: translateX(-30px);
    
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
`;

const slideRestItems = keyframes`
  from {
    transform: translateX(-15px);
  }
  to {
    transform: translateX(0);
  }
`;

const PinBadge = styled(Badge)<{ $isActive?: boolean; $isNew?: boolean }>`
  z-index: ${({ $isActive }) => ($isActive ? 1 : 0)};

  width: 50px;
  margin-right: 4px;

  font-size: 12px;
  color: black;
  white-space: nowrap;

  opacity: ${({ $isActive }) => ($isActive ? 1 : 0.5)};
  background-color: ${({ theme: { color }, $isActive }) =>
    $isActive ? color.magenta700 : color.disabledBackground};
  border: none;
  border-radius: 4px;

  transition: background-color 0.3s ease-in-out;
  animation: ${({ $isNew }) =>
    $isNew
      ? css`
          ${slideFirstItem} 0.6s forwards
        `
      : css`
          ${slideRestItems} 0.3s forwards
        `};
`;

const DeleteBadge = styled(Badge)`
  min-width: 30px;
  height: 30px;
  margin-right: 10px;
  padding: 0;

  border-radius: 50%;
`;
