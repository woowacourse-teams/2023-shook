import { useRef } from 'react';
import styled from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import PlayerBadge from '@/features/youtube/components/PlayerBadge';
import PlayingToggleButton from '@/features/youtube/components/PlayingToggleButton';
import ScrubberProgress, {
  ScrubberProgressAllPlaying,
} from '@/features/youtube/components/ScrubberProgress';
import SoundWave from '@/features/youtube/components/SoundWave';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';
import { secondsToMinSec, toMinSecText } from '@/shared/utils/convertTime';

const PROGRESS_WIDTH = 350;
const WaveScrubber = () => {
  const {
    interval,
    partStartTime,
    videoLength,
    isVideoStatePlaying,
    isAllPlay,
    updatePartStartTime,
    toggleAllPlay,
  } = useVoteInterfaceContext();
  const { videoPlayer, seekTo } = useVideoPlayerContext();
  const temptKey = useRef(0);
  const maxPartStartTime = videoLength - interval;
  const seekAndPlay = () => {
    seekTo(partStartTime);
  };

  const partStartTimeText = toMinSecText(partStartTime);

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

  const clickPlay = () => {
    if (isAllPlay) {
      videoPlayer.current?.playVideo();
    } else {
      seekTo(partStartTime);
    }
  };

  const clickPause = () => {
    videoPlayer.current?.pauseVideo();
  };

  const playWhenTouch = () => {
    if (!isVideoStatePlaying) {
      videoPlayer.current?.playVideo();
    }
  };

  useDebounceEffect<[number, number]>(seekAndPlay, [partStartTime, interval], 300);

  return (
    <>
      <BadgeContainer>
        <PlayerBadge>{partStartTimeText}</PlayerBadge>
        <PlayerBadge>
          <PlayingToggleButton
            pause={clickPause}
            play={clickPlay}
            isPlaying={isVideoStatePlaying}
          />
        </PlayerBadge>
        <PlayerBadge isActive={isAllPlay}>
          <button onClick={toggleAllPlay}>전체 듣기</button>
        </PlayerBadge>
      </BadgeContainer>
      <Spacing direction="vertical" size={8} />
      <Container>
        <Flex
          onScroll={changePartStartTime}
          onTouchStart={playWhenTouch}
          $gap={8}
          $css={{
            zIndex: 3,
            backgroundColor: 'transparent',
            alignItems: 'center',
            overflowX: 'scroll',
            width: '100%',
            height: '80px',
            padding: '0 calc((100% - 150px) / 2)',
          }}
        >
          <SoundWave waveLength={maxPartStartTime} />
        </Flex>
        <PlayingBox />
        {isVideoStatePlaying && !isAllPlay && <ScrubberProgress interval={interval} />}
        {isVideoStatePlaying && isAllPlay && <ScrubberProgressAllPlaying />}
      </Container>
    </>
  );
};

export default WaveScrubber;

const PlayingBox = styled.div`
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

const BadgeContainer = styled.div`
  display: flex;
  column-gap: 14px;
  justify-content: flex-end;
`;
