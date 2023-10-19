import styled, { keyframes } from 'styled-components';
import SoundWave from '@/features/killingParts/components/SoundWave';
import useWave from '@/features/killingParts/hooks/useWave';
import Flex from '@/shared/components/Flex/Flex';

const WaveScrubber = () => {
  const {
    waveScrubberRef,
    progressWidth,
    isInterval,
    isEntire,
    scrollStartTime,
    wheelStartTime,
    maxPartStartTime,
    interval,
    playVideo,
    dragStart,
    dragMoving,
    dragEnd,
  } = useWave();

  return (
    <Container>
      <WaveWrapper
        onScroll={scrollStartTime}
        onWheel={wheelStartTime}
        onTouchStart={playVideo}
        $progressWidth={progressWidth}
        ref={waveScrubberRef}
        $gap={8}
        $align="center"
        onMouseDown={dragStart}
        onMouseMove={dragMoving}
        onMouseUp={dragEnd}
        onMouseLeave={dragEnd}
      >
        <SoundWave ref={waveScrubberRef} length={maxPartStartTime} progressWidth={progressWidth} />
      </WaveWrapper>
      <ProgressFrame $progressWidth={progressWidth} />
      {isInterval && <ProgressFill $progressWidth={progressWidth} $interval={interval} />}
      {isEntire && <WaveFill $progressWidth={progressWidth} $isRunning={isEntire} />}
    </Container>
  );
};

export default WaveScrubber;

const WaveWrapper = styled(Flex)<{ $progressWidth: number }>`
  cursor: grab;

  z-index: 3;

  overflow-x: scroll;

  width: 100%;
  height: 75px;
  padding: ${({ $progressWidth }) => `0 calc((100% - ${$progressWidth}px) / 2)`};

  background-color: transparent;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    height: 90px;
  }
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

  width: ${({ $progressWidth }) => $progressWidth}px;
  height: 50px;

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

  width: ${({ $progressWidth }) => $progressWidth}px;
  height: 50px;

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

  width: ${({ $progressWidth }) => $progressWidth}px;
  height: 50px;

  background: ${({ theme: { color } }) =>
    `linear-gradient(to left, ${color.magenta100}, ${color.magenta400})`};
  border-radius: 5px;

  animation: ${waveFillAnimate} 4s ease-in-out infinite;
  animation-play-state: ${({ $isRunning }) => ($isRunning ? 'running' : 'paused')};
`;
