import styled, { keyframes } from 'styled-components';
import useCollectingPartContext from '@/features/songs/hooks/useCollectingPartContext';
import SoundWave from '@/features/youtube/components/SoundWave';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';

const WaveScrubber = () => {
  const { partStartTime, interval, videoLength, setPartStartTime, isPlayingEntire } =
    useCollectingPartContext();
  const video = useVideoPlayerContext();

  const maxPartStartTime = videoLength - interval;
  const isInterval = video.playerState === YT.PlayerState.PLAYING && !isPlayingEntire;
  const isEntire = video.playerState === YT.PlayerState.PLAYING && isPlayingEntire;
  const changePartStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;
    // ProgressFrame의 width: 350
    const unit = (scrollWidth - 350) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);

    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      setPartStartTime(partStartTimeToChange);
    }
  };

  const playVideo = () => {
    if (video.playerState === YT.PlayerState.PLAYING) {
      video.play();
    }
  };

  useDebounceEffect<[number, number]>(
    () => video.seekTo(partStartTime),
    [partStartTime, interval],
    300
  );

  return (
    <Container>
      <WaveWrapper onScroll={changePartStartTime} onTouchStart={playVideo} $gap={8} $align="center">
        <SoundWave length={maxPartStartTime} />
      </WaveWrapper>
      <ProgressFrame />
      {isInterval && <ProgressFill $interval={interval} />}
      {isPlayingEntire && <WaveFill $isRunning={isEntire} />}
    </Container>
  );
};

export default WaveScrubber;
const WaveWrapper = styled(Flex)`
  z-index: 3;

  overflow-x: scroll;

  width: 100%;
  height: 75px;
  padding: 0 calc((100% - 150px) / 2);

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
  position: absolute;
  z-index: 1;
  left: 50%;
  transform: translateX(-50%);

  width: 150px;
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
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

  width: 150px;
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
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

  width: 150px;
  height: 50px;

  background: ${({ theme: { color } }) =>
    `linear-gradient(to left, ${color.magenta100}, ${color.magenta400})`};
  border-radius: 5px;

  animation: ${waveFillAnimate} 4s ease-in-out infinite;
  animation-play-state: ${({ $isRunning }) => ($isRunning ? 'running' : 'paused')};
`;
