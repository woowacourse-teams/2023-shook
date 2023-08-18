/* eslint-disable react/display-name */
import { useEffect } from 'react';
import { styled } from 'styled-components';
import useVideoPlayerContext from '../hooks/useVideoPlayerContext';

interface YoutubeProps {
  videoId: string;
  start?: number;
}

const Youtube = ({ videoId, start = 0 }: YoutubeProps) => {
  const { videoPlayer, initPlayer, bindUpdatePlayerStateEvent } = useVideoPlayerContext();

  useEffect(() => {
    const createYoutubePlayer = async () => {
      try {
        new YT.Player(`yt-player-${videoId}`, {
          videoId,
          width: '100%',
          height: '100%',
          playerVars: { start, rel: 0, fs: 0 },
          events: {
            onReady: (e) => {
              bindUpdatePlayerStateEvent(e);
              initPlayer(e);
            },
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

      clonePlayerRef.current.destroy();
      clonePlayerRef.current = null;
    };
  }, [bindUpdatePlayerStateEvent, initPlayer, start, videoId, videoPlayer]);

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

  /* @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    width: 90%;
  } */
`;

export const YoutubeIframe = styled.div``;
