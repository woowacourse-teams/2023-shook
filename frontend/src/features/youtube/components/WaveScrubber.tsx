import { useRef } from 'react';
import styled from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import ScrubberProgress from '@/features/youtube/components/ScrubberProgress';
import SoundWave from '@/features/youtube/components/SoundWave';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';
import { secondsToMinSec } from '@/shared/utils/convertTime';
import PlayerState = YT.PlayerState;

const PROGRESS_WIDTH = 350;
const WaveScrubber = () => {
  const { interval, partStartTime, videoLength, updatePartStartTime } = useVoteInterfaceContext();
  const { playerState, seekTo } = useVideoPlayerContext();
  const temptKey = useRef(0);
  const maxPartStartTime = videoLength - interval;
  const seekAndPlay = () => {
    seekTo(partStartTime);
  };

  const changePartStartTime: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollWidth, scrollLeft } = e.currentTarget;
    const unit = (scrollWidth - PROGRESS_WIDTH) / maxPartStartTime;
    const partStartTimeToChange = Math.floor(scrollLeft / unit);

    if (partStartTimeToChange >= 0 && partStartTimeToChange <= maxPartStartTime) {
      const { minute, second } = secondsToMinSec(partStartTimeToChange);
      updatePartStartTime('minute', minute);
      updatePartStartTime('second', second);
      temptKey.current += temptKey.current + 1;
    }
  };

  useDebounceEffect<[number, number]>(seekAndPlay, [partStartTime, interval], 300);

  return (
    <Container>
      <Flex onScroll={changePartStartTime}>
        <SoundWave waveLength={maxPartStartTime} />
      </Flex>
      <PlayingBox />
      {playerState === PlayerState.PLAYING && (
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
