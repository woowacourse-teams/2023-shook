import styled from 'styled-components';

interface SoundWaveProps {
  waveLength: number;
}

const SoundWave = ({ waveLength }: SoundWaveProps) => {
  return Array.from({ length: waveLength }, () => (
    <>
      <LongBar />
      <ShortBar />
    </>
  ));
};

export default SoundWave;

const LongBar = styled.div`
  z-index: 2;
  left: calc(50%);

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
