/* eslint-disable react/display-name */
import { useEffect, useState } from 'react';
import { loadIFrameApi } from '@/components/Youtube/api/loadIframeApi';

interface YoutubeProps {
  videoId: string;
  start: number;

  onReady: (event: YT.PlayerEvent) => void;
}

const Youtube = ({ videoId, start, onReady }: YoutubeProps) => {
  const [player, setPlayer] = useState<YT.Player | undefined>();

  const createYoutubePlayer = async () => {
    try {
      const YT = await loadIFrameApi();

      setPlayer(
        new YT.Player('yt-player', {
          videoId,
          width: '400',
          height: '300',
          playerVars: { start, autoplay: 1 },
          events: {
            onReady: onPlayerReady,
            onStateChange: onPlayerStateChange,
          },
        })
      );
    } catch (error) {
      console.error(error);
      console.error('[createYoutubePlayer] Youtube Player를 생성하지 못하였습니다.');
    }
  };

  const onPlayerReady = (event: YT.PlayerEvent) => onReady(event);

  const onPlayerStateChange = (event: YT.OnStateChangeEvent) => {
    console.log('[onPlayerStateChange]');
    console.log(event.target);

    switch (event.data) {
      case YT.PlayerState.UNSTARTED:
        console.log('[시작전]');
        break;
      case YT.PlayerState.PLAYING:
        console.log('[재생]');
        break;
      case YT.PlayerState.PAUSED:
        console.log('[멈춤]');
        break;
      case YT.PlayerState.ENDED:
        console.log('[끝]');
        break;
      case YT.PlayerState.BUFFERING:
        console.log('[버퍼링]');
        break;
      case YT.PlayerState.CUED:
        console.log('[큐]');
        break;
      default:
        console.log('[NEVER]');
    }
  };

  useEffect(() => {
    createYoutubePlayer();
    return () => player?.destroy();
  }, [videoId]);

  return <div id="yt-player" />;
};

export default Youtube;
