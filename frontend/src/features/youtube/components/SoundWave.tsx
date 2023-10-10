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
  height: 24px;
  width: 4px;
  border-radius: 5px;
  background-color: ${({ theme: { color } }) => color.white};
  left: calc(50%);
`;

const ShortBar = styled.div`
  z-index: 2;
  height: 15px;
  width: 4px;
  border-radius: 5px;
  background-color: ${({ theme: { color } }) => color.white};
`;
