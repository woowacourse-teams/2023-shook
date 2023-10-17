import { useEffect, useRef } from 'react';
import styled, { css, keyframes } from 'styled-components';
import SoundWave from '@/features/killingParts/components/SoundWave';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';

const WaveScrubber = () => {
  const { interval, videoLength, setPartStartTime, isPlayingEntire } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const ref = useRef<HTMLDivElement | null>(null);
  // const progressWidth = 100 + (interval - 5) * 5;
  // const [, set] = useState();

  const maxPartStartTime = videoLength - interval;
  const isInterval = video.playerState === YT.PlayerState.PLAYING && !isPlayingEntire;
  const isEntire = video.playerState === YT.PlayerState.PLAYING && isPlayingEntire;

  const changePartStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;
    // WaverScrubber: 350
    // Frame: 100
    // calc((100% - 150px) / 2)
    // Padding: WaverScrubber - 150
    if (!ref.current) return;
    const clientWidth = ref.current?.clientWidth;
    const unit = (scrollWidth - clientWidth) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);
    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      setPartStartTime(partStartTimeToChange);
    }
  };

  const wheelPartStartTime: React.WheelEventHandler<HTMLDivElement> = (e) => {
    if (!ref.current) return;

    if (Math.abs(e.deltaX) < Math.abs(e.deltaY)) {
      e.currentTarget?.scrollTo({
        left: e.deltaY / 1.2 + ref.current?.scrollLeft,
        behavior: 'smooth',
      });
    }
  };

  const playVideo = () => {
    if (video.playerState !== YT.PlayerState.PLAYING) {
      video.play();
    }
  };

  // TODO: remove
  useEffect(() => {
    console.log(ref.current?.clientWidth);
  }, []);

  return (
    <Container>
      <WaveWrapper
        onScroll={changePartStartTime}
        onWheel={wheelPartStartTime}
        onTouchStart={playVideo}
        ref={ref}
        $gap={8}
        $align="center"
      >
        <SoundWave length={maxPartStartTime} />
      </WaveWrapper>
      <ProgressFrame />
      {isInterval && <ProgressFill $interval={interval} />}
      {isPlayingEntire && <WaveFill $isRunning={isEntire} />}
    </Container>
  );
};

export default WaveScrubber;

const ProgressWidth = css`
  width: 50px;
`;
const WaveWrapper = styled(Flex)`
  z-index: 3;

  overflow-x: scroll;

  width: 100%;
  height: 75px;
  padding: 0 calc((100% - 50px) / 2);

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

const ProgressFrame = styled.div`
  ${ProgressWidth};
  position: absolute;
  z-index: 1;
  left: 50%;
  transform: translateX(-50%);

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

const ProgressFill = styled.div<{ $interval: number }>`
  ${ProgressWidth};
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

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

const WaveFill = styled.div<{ $isRunning: boolean }>`
  ${ProgressWidth};
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

  height: 50px;

  background: ${({ theme: { color } }) =>
    `linear-gradient(to left, ${color.magenta100}, ${color.magenta400})`};
  border-radius: 5px;

  animation: ${waveFillAnimate} 4s ease-in-out infinite;
  animation-play-state: ${({ $isRunning }) => ($isRunning ? 'running' : 'paused')};
`;
