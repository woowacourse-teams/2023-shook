import { secondsToMinSec } from '@/utils/convertTime';
import { Slider, SliderWrapper } from './VideoSlider.style';
import type { TimeMinSec } from '../IntervalInput/IntervalInput.type';
import type { ChangeEventHandler } from 'react';

interface VideoSlider {
  time: number;
  videoLength: number;
  interval: number;
  setPartStart: (timeMinSec: TimeMinSec) => void;
  player: YT.Player | undefined;
}

const VideoSlider = ({ time, videoLength, interval, setPartStart, player }: VideoSlider) => {
  const changeTime: ChangeEventHandler<HTMLInputElement> = ({
    currentTarget: { valueAsNumber },
  }) => {
    const [minute, second] = secondsToMinSec(valueAsNumber);

    setPartStart({ minute, second });
    player?.pauseVideo();
    player?.seekTo(time, false);
  };

  const seekToTime = () => {
    player?.seekTo(time, true);
    player?.playVideo();
  };

  return (
    <SliderWrapper>
      <Slider
        type="range"
        value={time}
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
