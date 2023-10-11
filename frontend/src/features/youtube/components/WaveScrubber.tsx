import { useRef } from 'react';
import styled from 'styled-components';
import fillPlayIcon from '@/assets/icon/fill-play.svg';
import pauseIcon from '@/assets/icon/pause.svg';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import PlayerBadge from '@/features/youtube/components/PlayerBadge';
import ScrubberProgress, {
  ScrubberProgressAllPlaying,
} from '@/features/youtube/components/ScrubberProgress';
import SoundWave from '@/features/youtube/components/SoundWave';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
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

  const playWhenTouch = () => {
    if (isVideoStatePlaying) {
      videoPlayer.current?.playVideo();
    }
  };

  useDebounceEffect<[number, number]>(seekAndPlay, [partStartTime, interval], 300);

  return (
    <>
      <BadgeContainer>
        <PlayerBadge>{partStartTimeText}</PlayerBadge>
        <PlayerBadge>
          {isVideoStatePlaying ? (
            <Button onClick={videoPlayer.current?.pauseVideo}>
              <img src={pauseIcon} alt={'노래 정지'} />
            </Button>
          ) : (
            <Button
              onClick={() => {
                if (isAllPlay) {
                  videoPlayer.current?.playVideo();
                } else {
                  seekTo(partStartTime);
                }
              }}
            >
              <img src={fillPlayIcon} alt={'노래 시작'} />
            </Button>
          )}
        </PlayerBadge>
        <PlayerBadge isActive={isAllPlay}>
          <Button onClick={toggleAllPlay}>all</Button>
        </PlayerBadge>
      </BadgeContainer>
      <Spacing direction="vertical" size={12} />
      <Container>
        <Flex onScroll={changePartStartTime} onTouchStart={playWhenTouch}>
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

const Flex = styled.div`
  z-index: 3;
  display: flex;
  column-gap: 8px;
  background-color: transparent;
  align-items: center;
  overflow-x: scroll;
  width: 100%;
  height: 80px;

  padding: 0 calc((100% - 150px) / 2);
`;

const PlayingBox = styled.div`
  z-index: 1;
  width: 150px;
  height: 50px;

  border: transparent;
  border-radius: 4px;

  box-shadow: 0 0 0 2px inset ${({ theme: { color } }) => color.white};
  position: absolute;
  left: 50%;
  transform: translateX(-50%);

  pointer-events: none;
`;

const Container = styled.div`
  width: 100%;
  background-color: ${({ theme: { color } }) => color.secondary};

  margin: auto;
  border-radius: 8px;

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  &:active {
    transition: box-shadow 0.2s ease;
    box-shadow: 0 0 0 1px inset pink;
  }
`;

const BadgeContainer = styled.div`
  display: flex;
  justify-content: flex-end;
  column-gap: 14px;
`;

const Button = styled.button`
  width: 20px;
  display: flex;
  justify-content: center;
  align-items: center;
`;
