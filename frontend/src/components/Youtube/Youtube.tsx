/* eslint-disable react/display-name */
import { useEffect } from 'react';
import { styled } from 'styled-components';
import { loadIFrameApi } from '@/components/Youtube/api/loadIframeApi';
import useVoteInterfaceContext from '@/context/useVoteInterfaceContext';

interface YoutubeProps {
  videoId: string;
  start: number;
}

const Youtube = ({ videoId, start }: YoutubeProps) => {
  const { videoPlayer, updatePlayer } = useVoteInterfaceContext();

  const createYoutubePlayer = async () => {
    try {
      const YT = await loadIFrameApi();

      new YT.Player('yt-player', {
        videoId,
        width: '100%',
        height: '100%',
        playerVars: { start, autoplay: 1 },
        events: { onReady: updatePlayer },
      });
    } catch (error) {
      console.error(error);
      console.error('Youtube Player를 생성하지 못하였습니다.');
    }
  };

  useEffect(() => {
    createYoutubePlayer();

    return () => videoPlayer?.destroy();
  }, [videoId]);

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
