import React, { forwardRef } from 'react';
import styled from 'styled-components';

interface SoundWaveProps {
  length: number;
  progressWidth: number;
}
// eslint-disable-next-line react/display-name
const SoundWave = forwardRef<HTMLDivElement, SoundWaveProps>(
  ({ length, progressWidth }, boxRef) => {
    const refCallback =
      (activeHeight: string, inactiveHeight: string) => (dom: HTMLDivElement | null) => {
        if (!dom || !boxRef || typeof boxRef === 'function') return;
        if (boxRef.current?.scrollLeft) {
          const boxPos =
            boxRef.current?.scrollLeft + boxRef.current?.clientWidth / 2 - progressWidth / 2;

          const containerRightEdge = boxPos + progressWidth;
          const itemRightEdge = dom.offsetLeft;

          if (itemRightEdge >= boxPos && itemRightEdge <= containerRightEdge) {
            dom.style.height = activeHeight;
          } else {
            dom.style.height = inactiveHeight;
          }
        }
      };

    return Array.from({ length }, (_, index) => (
      <React.Fragment key={index}>
        <LongBar ref={refCallback('25px', '20px')} />
        <ShortBar ref={refCallback('17px', '12px')} />
      </React.Fragment>
    ));
  }
);

export default SoundWave;

const LongBar = styled.div`
  z-index: 2;
  left: 50%;

  width: 4px;
  height: 24px;

  background-color: ${({ theme: { color } }) => color.white};
  border-radius: 5px;

  transition: height 0.2s ease;
`;

const ShortBar = styled.div`
  z-index: 2;

  width: 4px;
  height: 15px;

  background-color: ${({ theme: { color } }) => color.white};
  border-radius: 5px;

  transition: height 0.2s ease;
`;
