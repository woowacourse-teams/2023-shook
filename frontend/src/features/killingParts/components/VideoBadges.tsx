import styled from 'styled-components';
import playIcon from '@/assets/icon/fill-play.svg';
import pauseIcon from '@/assets/icon/pause.svg';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import Flex from '@/shared/components/Flex/Flex';
import { toMinSecText } from '@/shared/utils/convertTime';

const VideoBadges = () => {
  const { partStartTime, isPlayingEntire, toggleEntirePlaying } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const partStartTimeText = toMinSecText(partStartTime);

  const isPaused = video.playerState === YT.PlayerState.PAUSED;

  const videoPlay = () => {
    if (isPlayingEntire) {
      video.play();
    } else {
      video.seekTo(partStartTime);
    }
  };
  const videoPause = () => {
    video.pause();
  };

  return (
    <Flex $gap={14} $justify="flex-end">
      <Badge>{partStartTimeText}</Badge>
      <Badge as="button" type="button" onClick={isPaused ? videoPlay : videoPause}>
        <img src={isPaused ? playIcon : pauseIcon} alt={'재생 혹은 정지'} />
      </Badge>
      <Badge as="button" type="button" $isActive={isPlayingEntire} onClick={toggleEntirePlaying}>
        전체 듣기
      </Badge>
    </Flex>
  );
};
export default VideoBadges;

const Badge = styled.span<{ $isActive?: boolean }>`
  display: flex;
  align-items: center;
  justify-content: center;

  height: 30px;
  min-width: 40px;
  padding: 0 10px;

  font-size: 14px;
  color: ${({ theme: { color }, $isActive }) => ($isActive ? color.black : color.white)};
  text-align: center;

  background-color: ${({ theme: { color }, $isActive }) =>
    $isActive ? color.magenta200 : color.disabled};
  border-radius: 40px;

  transition: background-color 0.2s ease-in;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;
