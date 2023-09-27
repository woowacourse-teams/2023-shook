/* eslint-disable react/display-name */
import { useCallback, useRef, useState } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import createObserver from '@/shared/utils/createObserver';
import useVideoPlayerContext from '../hooks/useVideoPlayerContext';

interface YoutubeProps {
  start?: number;
  videoId: string;
  songId: number;
}

const Youtube = ({ start = 0, videoId, songId }: YoutubeProps) => {
  const { initPlayer, bindUpdatePlayerStateEvent } = useVideoPlayerContext();
  const [loading, setLoading] = useState(true);

  const observerRef = useRef<IntersectionObserver | null>();
  const iframeRef = useRef<IntersectionObserver | null>(null);

  const navigate = useNavigate();
  const location = useLocation();

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

  const navigateToCurrentSongId: React.RefCallback<HTMLImageElement> = useCallback((domNode) => {
    const navigateToCurrentSong = () => {
      const [, songs, , genre] = location.pathname.split('/');

      navigate(`/${songs}/${songId}/${genre}`, {
        replace: true,
        preventScrollReset: true,
      });
    };

    if (domNode !== null) {
      iframeRef.current = createObserver(navigateToCurrentSong);
      iframeRef.current.observe(domNode);

      return;
    }

    iframeRef.current?.disconnect();
  }, []);

  return (
    <YoutubeWrapper>
      {loading && (
        <Preview
          src={`https://img.youtube.com/vi/${videoId}/hqdefault.jpg`}
          ref={createPlayerOnObserve}
          loading="lazy"
        />
      )}
      <YoutubeIframe ref={navigateToCurrentSongId} id={`yt-player-${videoId}`} />
    </YoutubeWrapper>
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
