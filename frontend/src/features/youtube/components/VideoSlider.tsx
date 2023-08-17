import { styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import { secondsToMinSec, toMinSecText } from '@/shared/utils/convertTime';
import type { ChangeEventHandler } from 'react';

const VideoSlider = () => {
  const { interval, partStartTime, videoLength, updatePartStartTime } = useVoteInterfaceContext();
  const { videoPlayer } = useVideoPlayerContext();

  const partEndTime = partStartTime + interval;
  const partStartTimeText = toMinSecText(partStartTime);
  const partEndTimeText = toMinSecText(partEndTime);

  const changeTime: ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { valueAsNumber: currentSelectedTime },
  }) => {
    const { minute, second } = secondsToMinSec(currentSelectedTime);

    // TODO: 시간 단위 통일
    updatePartStartTime('minute', minute);
    updatePartStartTime('second', second);

    videoPlayer?.seekTo(currentSelectedTime, false);
  };

  const seekToTime = () => {
    videoPlayer?.seekTo(partStartTime, true);
    videoPlayer?.playVideo();
  };

  return (
    <SliderWrapper>
      <SliderBox>
        <PartStartTime>{partStartTimeText}</PartStartTime>
        <Slider
          type="range"
          value={partStartTime}
          onChange={changeTime}
          onTouchEnd={seekToTime}
          onMouseUp={seekToTime}
          min={0}
          max={videoLength - interval}
          step={1}
          interval={interval}
        />
        <PartEndTime>{partEndTimeText}</PartEndTime>
      </SliderBox>
    </SliderWrapper>
  );
};

export default VideoSlider;

const SliderWrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

export const SliderBox = styled.div`
  position: relative;

  display: flex;
  align-items: center;
  justify-content: center;

  width: 90%;
`;

export const PartStartTime = styled.span`
  position: absolute;
  top: -14px;
  left: -14px;

  font-size: 14px;
  font-weight: 700;
`;

export const PartEndTime = styled.span`
  position: absolute;
  top: -14px;
  right: -14px;

  font-size: 14px;
  font-weight: 700;
`;

const Slider = styled.input<{ interval: number }>`
  cursor: pointer;

  width: 100%;
  height: 40px;

  -webkit-appearance: none;
  background: transparent;

  &::-webkit-slider-thumb {
    position: relative;
    top: -4px;

    width: ${({ interval }) => interval * 6}px;
    height: 16px;

    -webkit-appearance: none;
    background-color: ${({ theme: { color } }) => color.primary};
    border: none;
    border-radius: 20px;
  }

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
`;
