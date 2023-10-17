import { useRef, useState } from 'react';
import styled, { keyframes } from 'styled-components';
import SoundWave from '@/features/killingParts/components/SoundWave';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';

const WaveScrubber = () => {
  const { interval, videoLength, setPartStartTime, isPlayingEntire } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const boxRef = useRef<HTMLDivElement>(null);
  const [xPos, setXPos] = useState<{ initial: number; scroll: number } | null>(null);

  console.log('box scroll', boxRef.current?.scrollLeft);

  const maxPartStartTime = videoLength - interval;
  const progressWidth = 100 + (interval - 5) * 5;
  const isInterval = video.playerState === YT.PlayerState.PLAYING && !isPlayingEntire;
  const isEntire = video.playerState === YT.PlayerState.PLAYING && isPlayingEntire;

  const changePartStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;

    if (!boxRef.current) return;
    const clientWidth = boxRef.current?.clientWidth;
    const unit = (scrollWidth - clientWidth) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);
    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      setPartStartTime(partStartTimeToChange);
    }
  };

  const wheelPartStartTime: React.WheelEventHandler<HTMLDivElement> = (e) => {
    console.log('[wheel]');
    if (!boxRef.current) return;

    if (Math.abs(e.deltaX) < Math.abs(e.deltaY)) {
      e.currentTarget?.scrollTo({
        left: e.deltaY / 1.2 + boxRef.current?.scrollLeft,
        behavior: 'smooth',
      });
    }
  };

  const playVideo = () => {
    if (video.playerState !== YT.PlayerState.PLAYING) {
      video.play();
    }
  };

  return (
    <Container>
      <WaveWrapper
        onScroll={changePartStartTime}
        onWheel={wheelPartStartTime}
        onTouchStart={playVideo}
        $progressWidth={progressWidth}
        ref={boxRef}
        $gap={8}
        $align="center"
        onMouseDown={(e) => {
          e.preventDefault();
          if (boxRef.current) {
            setXPos({
              initial: e.screenX,
              scroll: boxRef.current?.scrollLeft,
            });
          }
        }}
        onMouseMove={({ screenX }) => {
          if (!xPos) return;
          console.log('[mouse move]');

          boxRef.current?.scrollTo({
            left: xPos.scroll + (xPos.initial - screenX) / 0.5,
            behavior: 'instant',
          });
        }}
        onMouseUp={() => {
          console.log('[mouse up]');
          setXPos(null);
        }}
        onMouseLeave={() => {
          console.log('[mouse leave]');
          setXPos(null);
        }}
      >
        <SoundWave ref={boxRef} length={maxPartStartTime} progressWidth={progressWidth} />
      </WaveWrapper>
      <ProgressFrame $progressWidth={progressWidth} />
      {isInterval && <ProgressFill $progressWidth={progressWidth} $interval={interval} />}
      {isPlayingEntire && <WaveFill $progressWidth={progressWidth} $isRunning={isEntire} />}
    </Container>
  );
};

export default WaveScrubber;

const WaveWrapper = styled(Flex)<{ $progressWidth: number }>`
  z-index: 3;
  cursor: grab;

  overflow-x: scroll;

  width: 100%;
  height: 75px;
  padding: ${({ $progressWidth }) => `0 calc((100% - ${$progressWidth}px) / 2)`};

  background-color: transparent;
`;

const Container = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  width: 100%;
  margin: auto;

  background-color: ${({ theme: { color } }) => color.secondary};
  border-radius: 8px;

  &:active {
    box-shadow: 0 0 0 1px inset ${({ theme: { color } }) => color.magenta300};
    transition: box-shadow 0.2s ease;
  }
`;

const ProgressFrame = styled.div<{ $progressWidth: number }>`
  position: absolute;
  z-index: 1;
  left: 50%;
  transform: translateX(-50%);

  height: 50px;
  width: ${({ $progressWidth }) => $progressWidth}px;

  border: transparent;
  border-radius: 4px;
  box-shadow: 0 0 0 2px inset ${({ theme: { color } }) => color.white};
`;

const fillAnimation = keyframes`
  0% {
    background-position: right;
  }
  100% {
    background-position: left;
  }
`;

const ProgressFill = styled.div<{ $progressWidth: number; $interval: number }>`
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

  height: 50px;
  width: ${({ $progressWidth }) => $progressWidth}px;

  background: ${({ theme: { color } }) =>
    `linear-gradient(to left, transparent 50%, ${color.magenta300} 50%)`};
  background-size: 200%;
  border-radius: 5px;

  animation: ${fillAnimation} ${({ $interval }) => $interval}s linear infinite;
`;

const waveFillAnimate = keyframes`
  0%, 100% {
    clip-path: polygon(
      0% 45%,
      16% 44%,
      33% 50%,
      54% 60%,
      70% 61%,
      84% 59%,
      100% 52%,
      100% 100%,
      0% 100%
    );
  }

  50% {
    clip-path: polygon(
      0% 60%,
      15% 65%,
      34% 66%,
      51% 62%,
      67% 50%,
      84% 45%,
      100% 46%,
      100% 100%,
      0% 100%
    );
  }
`;

const WaveFill = styled.div<{ $progressWidth: number; $isRunning: boolean }>`
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

  height: 50px;
  width: ${({ $progressWidth }) => $progressWidth}px;

  background: ${({ theme: { color } }) =>
    `linear-gradient(to left, ${color.magenta100}, ${color.magenta400})`};
  border-radius: 5px;

  animation: ${waveFillAnimate} 4s ease-in-out infinite;
  animation-play-state: ${({ $isRunning }) => ($isRunning ? 'running' : 'paused')};
`;
