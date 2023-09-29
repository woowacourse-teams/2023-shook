/* eslint-disable react/display-name */
import { useCallback, useRef, useState } from 'react';
import { styled } from 'styled-components';
import Flex from '@/shared/components/Flex/Flex';
import createObserver from '@/shared/utils/createObserver';
import useVideoPlayerContext from '../hooks/useVideoPlayerContext';

interface YoutubeProps {
  videoId: string;
  start?: number;
}

const Youtube = ({ videoId, start = 0 }: YoutubeProps) => {
  const { initPlayer, bindUpdatePlayerStateEvent } = useVideoPlayerContext();
  const [loading, setLoading] = useState(true);
  const observerRef = useRef<IntersectionObserver | null>();

  const createPlayerOnObserve: React.RefCallback<HTMLImageElement> = useCallback((domNode) => {
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
              setLoading(false);
            },
          },
        });
      } catch (error) {
        console.error(error);
        console.error('Youtube Player를 생성하지 못하였습니다.');
      }
    };

    if (domNode !== null) {
      observerRef.current = createObserver(createYoutubePlayer);
      observerRef.current.observe(domNode);
      return;
    }

    observerRef.current?.disconnect();
  }, []);

  return (
    <Flex align="center" justify="center">
      <YoutubeWrapper>
        {loading && (
          <Preview
            src={`https://img.youtube.com/vi/${videoId}/hqdefault.jpg`}
            ref={createPlayerOnObserve}
            loading="lazy"
          />
        )}
        <YoutubeIframe id={`yt-player-${videoId}`} />
      </YoutubeWrapper>
    </Flex>
  );
};

export default Youtube;

export const YoutubeWrapper = styled.div`
  position: relative;
  aspect-ratio: 16 / 9;
  width: 100%;
`;

export const YoutubeIframe = styled.div``;

const Preview = styled.img`
  position: absolute;
  aspect-ratio: 16 / 9;
  width: 100%;
  object-fit: cover;
`;
