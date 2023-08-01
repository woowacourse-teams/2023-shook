import useVoteInterfaceContext from '@/context/useVoteInterfaceContext';
import { minSecToSeconds, secondsToMinSec } from '@/utils/convertTime';
import { Slider, SliderWrapper } from './VideoSlider.style';
import type { ChangeEventHandler } from 'react';

interface VideoSlider {
  videoLength: number;
}

const VideoSlider = ({ videoLength }: VideoSlider) => {
  const { interval, partStartTime, videoPlayer, updatePartStartTime } = useVoteInterfaceContext();
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
