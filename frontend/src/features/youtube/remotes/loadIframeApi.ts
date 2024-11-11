import { loadScript } from '@/shared/utils/loadScript';

// TODO: index.d.ts
declare global {
  interface Window {
    onYouTubeIframeAPIReady: () => void;
  }
}

function loadIFrameApi(): Promise<typeof YT> {
  return new Promise((resolve, reject) => {
    if (typeof window.YT === 'object') {
      resolve(window.YT);
      return;
    }

    try {
      loadScript('https://www.youtube.com/iframe_api');
    } catch (error) {
      reject(error);
    }

    window.onYouTubeIframeAPIReady = () => {
      resolve(window.YT);
    };
  });
}

export { loadIFrameApi };
