import styled from 'styled-components';
import fillPlayIcon from '@/assets/icon/fill-play.svg';
import pauseIcon from '@/assets/icon/pause.svg';

interface PlayingToggleButtonProps {
  pause: () => void;
  play: () => void;
  isPlaying: boolean;
}

const PlayingToggleButton = ({ pause, play, isPlaying }: PlayingToggleButtonProps) => {
  return (
    <>
      {isPlaying ? (
        <Button onClick={pause}>
          <img src={pauseIcon} alt={'노래 정지'} />
        </Button>
      ) : (
        <Button onClick={play}>
          <img src={fillPlayIcon} alt={'노래 시작'} />
        </Button>
      )}
    </>
  );
};

export default PlayingToggleButton;

const Button = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 20px;
`;
