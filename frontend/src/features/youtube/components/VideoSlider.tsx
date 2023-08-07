import { styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import useVideoPlayerContext from '@/features/youtube/contexts/useVideoPlayerContext';
import { minSecToSeconds, secondsToMinSec } from '@/shared/utils/convertTime';
import type { ChangeEventHandler } from 'react';

interface VideoSlider {
  videoLength: number;
}

const VideoSlider = ({ videoLength }: VideoSlider) => {
  const { interval, partStartTime, updatePartStartTime } = useVoteInterfaceContext();
  const { videoPlayer } = useVideoPlayerContext();
  const partStartTimeInSeconds = minSecToSeconds([partStartTime.minute, partStartTime.second]);

  const changeTime: ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { valueAsNumber: currentSelectedTime },
  }) => {
    const [minute, second] = secondsToMinSec(currentSelectedTime);

    // TODO: 시간 단위 통일
    updatePartStartTime('minute', minute);
    updatePartStartTime('second', second);
    videoPlayer?.pauseVideo();
    videoPlayer?.seekTo(currentSelectedTime, false);
  };

  const seekToTime = () => {
    videoPlayer?.seekTo(partStartTimeInSeconds, true);
    videoPlayer?.playVideo();
  };

  return (
    <SliderWrapper>
      <Slider
        type="range"
        value={partStartTimeInSeconds}
        onChange={changeTime}
        onTouchEnd={seekToTime}
        onMouseUp={seekToTime}
        min={0}
        max={videoLength - interval}
        step={1}
        interval={interval}
      />
    </SliderWrapper>
  );
};

export default VideoSlider;

const SliderWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Slider = styled.input<{ interval: number }>`
  width: 100%;
  height: 40px;

  -webkit-appearance: none;
  background: transparent;

  cursor: pointer;

  &:active {
    cursor: grabbing;
  }

  &:focus {
    outline: none;
  }

  // TODO: webkit, moz cross browsing
  // https://css-tricks.com/styling-cross-browser-compatible-range-inputs-css/
  &::-webkit-slider-runnable-track {
    width: 100%;
    height: 8px;
    background-color: gray;
    border: none;
    border-radius: 5px;
  }

  &::-webkit-slider-thumb {
    position: relative;
    -webkit-appearance: none;
    top: -4px;
    width: ${({ interval }) => interval * 6}px;
    height: 16px;
    background-color: ${({ theme: { color } }) => color.primary};
    border: none;
    border-radius: 20px;
  }
`;
