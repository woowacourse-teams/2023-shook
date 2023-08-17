/* eslint-disable react/display-name */
import { useCallback, useEffect } from 'react';
import { styled } from 'styled-components';
import { loadIFrameApi } from '@/features/youtube/remotes/loadIframeApi';
import useVideoPlayerContext from '../hooks/useVideoPlayerContext';

interface YoutubeProps {
  videoId: string;
  start?: number;
}

const Youtube = ({ videoId, start = 0 }: YoutubeProps) => {
  const { videoPlayer, updatePlayer } = useVideoPlayerContext();

  const createYoutubePlayer = useCallback(async () => {
    try {
      const YT = await loadIFrameApi();

      updatePlayer(
        new YT.Player('yt-player', {
          videoId,
          width: '100%',
          height: '100%',
          playerVars: { start },
        })
      );
    } catch (error) {
      console.error(error);
      console.error('Youtube Player를 생성하지 못하였습니다.');
    }
  }, []);

  useEffect(() => {
    createYoutubePlayer();

    return () => videoPlayer?.destroy();
  }, []);

  return (
    <YoutubeWrapper>
      <YoutubeIframe id="yt-player" />
    </YoutubeWrapper>
  );
};

export default Youtube;

export const YoutubeWrapper = styled.div`
  aspect-ratio: auto 16 / 9;
  width: 100%;
`;

export const YoutubeIframe = styled.div``;
