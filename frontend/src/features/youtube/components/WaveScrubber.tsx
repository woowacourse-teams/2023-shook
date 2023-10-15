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

  const changePartStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;
    const unit = (scrollWidth - 350) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);

    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      setPartStartTime(partStartTimeToChange);
    }
  };

  const playWhenTouch = () => {
    if (video.playerState !== 1) {
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
      <WaveWrapper
        onScroll={changePartStartTime}
        onTouchStart={playWhenTouch}
        $gap={8}
        $align="center"
      >
        <SoundWave length={maxPartStartTime} />
      </WaveWrapper>
      <ProgressFrame />
      {video.playerState === 1 && !isPlayingEntire && <ProgressFill $interval={interval} />}
      {video.playerState === 1 && isPlayingEntire && <WaveFill />}
    </Container>
  );
};

export default WaveScrubber;
const WaveWrapper = styled(Flex)`
  z-index: 3;
  background-color: transparent;
  overflow-x: scroll;
  width: 100%;
  height: 75px;
  padding: 0 calc((100% - 150px) / 2);
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
    box-shadow: 0 0 0 1px inset pink;
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

  background: linear-gradient(to left, transparent 50%, pink 50%);
  background-size: ${({ $interval }) => 200 + (30 - $interval)}%;
  border-radius: 5px;

  transition: 10s linear;
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

const WaveFill = styled.div`
  pointer-events: none;

  position: absolute;
  z-index: 0;
  left: 50%;
  transform: translateX(-50%);

  width: 150px;
  height: 50px;

  background: linear-gradient(to left, deeppink, pink);
  border-radius: 5px;

  transition: 10s linear;
  animation: ${waveFillAnimate} 4s ease-in-out infinite;
`;
