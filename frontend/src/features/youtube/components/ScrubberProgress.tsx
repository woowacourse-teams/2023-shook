import styled, { keyframes } from 'styled-components';

interface ScrubberProgressProps {
  interval: number;
}

const ScrubberProgress = ({ interval }: ScrubberProgressProps) => {
  return <PlayingBoxBackground interval={interval} />;
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

const PlayingBoxBackground = styled.div<{ interval: number }>`
  z-index: 0;
  width: 150px;
  height: 50px;
  border-radius: 5px;

  position: absolute;
  left: 50%;
  transform: translateX(-50%);

  background: linear-gradient(to left, transparent 50%, pink 50%);
  background-size: ${({ interval }) => 200 + (30 - interval)}%;
  transition: 10s linear;
  animation: ${fillAnimation} ${({ interval }) => interval}s linear infinite;

  pointer-events: none;
`;

export const ScrubberProgressAllPlaying = () => {
  return <AllPlayingBackground />;
};

const animate = keyframes`
  0%, 100% {
    clip-path: polygon(
      0% 45%,
      16% 44%,
      33% 50%,
      54% 60%,
      70% 61%,
      84% 59%,
      100% 52%,
      100% 100%,
      0% 100%
    );
  }

  50% {
    clip-path: polygon(
      0% 60%,
      15% 65%,
      34% 66%,
      51% 62%,
      67% 50%,
      84% 45%,
      100% 46%,
      100% 100%,
      0% 100%
    );
  }
`;

const AllPlayingBackground = styled.div`
  z-index: 0;
  width: 150px;
  height: 50px;
  border-radius: 5px;

  position: absolute;
  left: 50%;
  transform: translateX(-50%);

  background: linear-gradient(to left, deeppink, pink);
  transition: 10s linear;

  animation: ${animate} 4s ease-in-out infinite;

  pointer-events: none;
`;
