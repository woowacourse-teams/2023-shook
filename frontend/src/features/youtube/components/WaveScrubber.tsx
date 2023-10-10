import { useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import ScrubberProgress from '@/features/youtube/components/ScrubberProgress';
import SoundWave from '@/features/youtube/components/SoundWave';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import { secondsToMinSec } from '@/shared/utils/convertTime';

export function useDebounce<T>(value: T, delay?: number): T {
  const [debouncedValue, setDebouncedValue] = useState<T>(value);

  useEffect(() => {
    const timer = setTimeout(() => setDebouncedValue(value), delay || 500);

    return () => {
      clearTimeout(timer);
    };
  }, [value, delay]);

  return debouncedValue;
}

const WaveScrubber = () => {
  const { interval, partStartTime, videoLength, updatePartStartTime } = useVoteInterfaceContext();
  const { videoPlayer, playerState } = useVideoPlayerContext();

  const temptKey = useRef(0);
  const debouncedValue = useDebounce<number>(partStartTime, 1000);

  const maxPartStartTime = videoLength - interval;

  useEffect(() => {
    videoPlayer.current?.seekTo(partStartTime, true);
    videoPlayer.current?.playVideo();
  }, [debouncedValue]);

  return (
    <Container>
      <Flex
        onScroll={(e) => {
          const { scrollWidth, scrollLeft } = e.currentTarget;
          const unit = (scrollWidth - 350) / maxPartStartTime; //350: 전체 width
          const timeScrolled = Math.floor(scrollLeft / unit);

          if (timeScrolled >= 0 && timeScrolled <= maxPartStartTime) {
            const { minute, second } = secondsToMinSec(timeScrolled);
            updatePartStartTime('minute', minute);
            updatePartStartTime('second', second);
            temptKey.current += temptKey.current + 1;
          }
        }}
      >
        <SoundWave waveLength={maxPartStartTime} />
      </Flex>
      <PlayingBox />
      {playerState === 1 && (
        <ScrubberProgress
          key={temptKey.current}
          prevTime={0}
          interval={interval}
          isPaused={playerState !== 1}
        />
      )}
    </Container>
  );
};

export default WaveScrubber;

const Flex = styled.div`
  z-index: 3;
  display: flex;
  column-gap: 8px;
  background-color: transparent;
  align-items: center;
  overflow-x: scroll;
  width: 100%;
  height: 100px;

  border: 1px solid darkorange;

  padding: 0 calc((100% - 150px) / 2);
`;

const PlayingBox = styled.div`
  z-index: 1;
  width: 150px;
  height: 50px;

  border: none;
  border-radius: 4px;

  box-shadow: 0 0 0 3px inset grey;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);

  pointer-events: none;
`;

const Container = styled.div`
  width: 100%;
  background-color: black;
  border: 2px solid greenyellow;
  margin: auto;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
