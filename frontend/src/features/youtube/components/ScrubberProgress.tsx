import styled, { keyframes } from 'styled-components';

interface ScrubberProgressProps {
  prevTime: number;
  interval: number;
  isPaused: boolean;
}

const ScrubberProgress = ({ prevTime, interval, isPaused }: ScrubberProgressProps) => {
  return <PlayingBoxBackground prevTime={prevTime} interval={interval} isPaused={isPaused} />;
};

export default ScrubberProgress;

const fillAnimation = keyframes`
  0% {
    background-position: right;
  }
  100% {
    background-position: left;
  }
`;

const PlayingBoxBackground = styled.div<{ prevTime: number; interval: number; isPaused: boolean }>`
  z-index: 0;
  width: 150px;
  height: 50px;
  border-radius: 5px;

  position: absolute; // 화면에 고정된 위치
  left: 50%; // 뷰포트의 중앙에 위치
  transform: translateX(-50%); // 뷰포트 중앙

  background: linear-gradient(to left, transparent 50%, pink 50%);
  background-size: ${({ interval }) => 200 + (30 - interval)}%;
  transition: 10s linear;
  animation: ${fillAnimation} ${({ interval }) => interval}s linear infinite;
  animation-play-state: ${({ isPaused }) => (isPaused ? 'paused' : 'running')};
  animation-delay: ${({ prevTime }) => -prevTime}s;

  pointer-events: none;
`;
