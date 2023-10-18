import React from 'react';
import styled from 'styled-components';

interface SoundWaveProps {
  length: number;
}
const SoundWave = ({ length }: SoundWaveProps) => {
  return Array.from({ length }, (_, index) => {
    return (
      <React.Fragment key={index}>
        <LongBar />
        <ShortBar />
      </React.Fragment>
    );
  });
};

export default SoundWave;

const LongBar = styled.div`
  z-index: 2;
  left: 50%;

  width: 4px;
  height: 24px;

  background-color: ${({ theme: { color } }) => color.white};
  border-radius: 5px;
`;

const ShortBar = styled.div`
  z-index: 2;

  width: 4px;
  height: 15px;

  background-color: ${({ theme: { color } }) => color.white};
  border-radius: 5px;
`;
