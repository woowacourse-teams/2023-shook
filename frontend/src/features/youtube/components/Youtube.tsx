/* eslint-disable react/display-name */
import { useEffect } from 'react';
import { styled } from 'styled-components';
import { loadIFrameApi } from '@/features/youtube/remotes/loadIframeApi';
import useVideoPlayerContext from '../hooks/useVideoPlayerContext';

interface YoutubeProps {
  videoId: string;
  start?: number;
}

const Youtube = ({ videoId, start = 0 }: YoutubeProps) => {
  const { videoPlayer, initPlayer, updatePlayerState } = useVideoPlayerContext();

  useEffect(() => {
    const createYoutubePlayer = async () => {
      try {
        const YT = await loadIFrameApi();

        new YT.Player(`yt-player-${videoId}`, {
          videoId,
          width: '100%',
          height: '100%',
          playerVars: { start, rel: 0 },
          events: {
            onReady: initPlayer,
            onStateChange: updatePlayerState,
          },
        });
      } catch (error) {
        console.error(error);
        console.error('Youtube Player를 생성하지 못하였습니다.');
      }
    };

    createYoutubePlayer();

    const clonePlayerRef = videoPlayer;

    return () => {
      if (!clonePlayerRef.current) return;

      clonePlayerRef.current?.destroy();
      clonePlayerRef.current = null;
    };
  }, [initPlayer, updatePlayerState, start, videoId, videoPlayer]);

  return (
    <YoutubeWrapper>
      <YoutubeIframe id={`yt-player-${videoId}`} />
    </YoutubeWrapper>
  );
};

export default Youtube;

export const YoutubeWrapper = styled.div`
  aspect-ratio: auto 16 / 9;
  width: 100%;
`;

export const YoutubeIframe = styled.div``;
