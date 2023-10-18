import { useMemo, useRef, useState } from 'react';
import styled, { css, keyframes } from 'styled-components';
import playIcon from '@/assets/icon/fill-play.svg';
import pauseIcon from '@/assets/icon/pause.svg';
import pinIcon from '@/assets/icon/pin.svg';
import playStreamIcon from '@/assets/icon/play-stream.svg';
import removeIcon from '@/assets/icon/remove.svg';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';
import { toMinSecText } from '@/shared/utils/convertTime';

interface Pin {
  partStartTime: number;
  interval: number;
  text: string;
}
const VideoBadges = () => {
  const {
    partStartTime,
    isPlayingEntire,
    interval,
    setPartStartTime,
    setInterval,
    toggleEntirePlaying,
  } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const partStartTimeText = toMinSecText(partStartTime);
  const ref = useRef<HTMLDivElement>(null);

  const [pinList, setPinList] = useState<Pin[]>([]);

  const activePinIndex = useMemo(
    () =>
      pinList.findIndex((pin) => pin.partStartTime === partStartTime && pin.interval === interval),
    [pinList, partStartTime, interval]
  );

  const isPinListEmpty = pinList.length === 0;

  const pinAnimationRef = useRef<number>(1);
  const refreshPinAnimation = () => {
    pinAnimationRef.current += 1;
  };

  const addPin = () => {
    const text = `${toMinSecText(partStartTime)}`;

    setPinList((prevTimeList) => [
      {
        partStartTime,
        interval,
        text,
      },
      ...prevTimeList.filter((pin) => pin.text !== text),
    ]);

    if (ref.current) {
      ref.current.scrollTo({
        left: 0,
        behavior: 'smooth',
      });
    }

    refreshPinAnimation();
  };

  const deletePin = () => {
    if (activePinIndex >= 0) {
      setPinList(pinList.filter((_, index) => index !== activePinIndex));
    } else {
      setPinList(pinList.slice(1));
    }
  };

  const playPin = (start: number, interval: number) => () => {
    setPartStartTime(start);
    setInterval(interval);
  };

  const isPaused = video.playerState === YT.PlayerState.PAUSED;

  const videoPlay = () => {
    if (isPlayingEntire) {
      video.play();
    } else {
      video.seekTo(partStartTime);
    }
  };
  const videoPause = () => {
    video.pause();
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
      <PinFlex $gap={4} ref={ref}>
        {!isPinListEmpty && (
          <DeleteBadge as="button" onClick={deletePin}>
            <img src={removeIcon} alt="나만의 파트 임시 저장 삭제하기" />
          </DeleteBadge>
        )}
        {pinList.map((pin, index) => (
          <PinBadge
            key={pin.text + pinAnimationRef.current}
            as="button"
            onClick={playPin(pin.partStartTime, pin.interval)}
            $isActive={index === activePinIndex}
            $isNew={index === 0 && index === activePinIndex}
          >
            {pin.text}
          </PinBadge>
        ))}
      </PinFlex>
    </>
  );
};
export default VideoBadges;

const Badge = styled.span<{ $isActive?: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 30px;
  min-width: 40px;
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

const PinFlex = styled(Flex)`
  overflow-x: scroll;
  position: relative;
`;

const slideLeft = keyframes`
  from {
    opacity: 0;
    transform: translateX(-30px);
    
  }
  to {
    opacity: 1;
    transform: translateX(0);
  }
`;

const slideRight = keyframes`
  from {
    transform: translateX(-15px);
  }
  to {
    transform: translateX(0);
  }
`;

const PinBadge = styled(Badge)<{ $isActive?: boolean; $isNew?: boolean }>`
  background-color: ${({ theme: { color }, $isActive }) =>
    $isActive ? color.magenta700 : color.disabledBackground};

  z-index: ${({ $isActive }) => ($isActive ? 1 : 0)};
  opacity: ${({ $isActive }) => ($isActive ? 1 : 0.5)}

  border: none;
  width: 50px;
  white-space: nowrap;

  color: ${({ theme: { color }, $isActive }) => ($isActive ? color.white : color.black)};
  font-size: 12px;
  margin-right: 4px;
  border-radius: 4px;

  transition: background-color 0.3s ease-in-out;
  animation: ${({ $isNew }) =>
    $isNew
      ? css`
          ${slideLeft} 1s forwards
        `
      : css`
          ${slideRight} 0.5s forwards
        `};
`;

const DeleteBadge = styled(Badge)`
  border-radius: 50%;
  height: 30px;
  min-width: 30px;
  padding: 0;
  margin-right: 10px;
`;
