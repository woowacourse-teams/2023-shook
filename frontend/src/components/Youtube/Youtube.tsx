/* eslint-disable react/display-name */
import { useCallback, useEffect } from 'react';
import { styled } from 'styled-components';
import { useVideoPlayerContext } from '@/components/Youtube';
import { loadIFrameApi } from '@/components/Youtube/api/loadIframeApi';

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
  width: 100%;
  aspect-ratio: auto 16 / 9;
`;

export const YoutubeIframe = styled.div``;
