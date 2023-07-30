import { secondsToMinSec } from '@/utils/convertTime';
import { Slider, SliderWrapper } from './VideoSlider.style';
import type { TimeMinSec } from '../IntervalInput/IntervalInput.type';
import type { ChangeEventHandler } from 'react';

interface VideoSlider {
  partStartTime: number;
  videoLength: number;
  interval: number;
  setPartStartTime: (timeMinSec: TimeMinSec) => void;
  player: YT.Player | undefined;
}

const VideoSlider = ({
  partStartTime,
  videoLength,
  interval,
  setPartStartTime,
  player,
}: VideoSlider) => {
  const changeTime: ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { valueAsNumber },
  }) => {
    const [minute, second] = secondsToMinSec(valueAsNumber);

    setPartStartTime({ minute, second });
    player?.pauseVideo();
    player?.seekTo(partStartTime, false);
  };

  const seekToTime = () => {
    player?.seekTo(partStartTime, true);
    player?.playVideo();
  };

  return (
    <SliderWrapper>
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
    </SliderWrapper>
  );
};

export default VideoSlider;
