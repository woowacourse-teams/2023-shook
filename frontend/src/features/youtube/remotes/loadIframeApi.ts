// TODO: index.d.ts
declare global {
  interface Window {
    onYouTubeIframeAPIReady: () => void;
  }
}

function load(cb: (err: Error) => void) {
  const firstScript = document.getElementsByTagName('script')[0];
  const script = document.createElement('script');

  script.src = 'https://www.youtube.com/iframe_api';
  script.async = true;
  script.onerror = function () {
    this.onload = null;
    this.onerror = null;
    cb(new Error(`Failed to load ${this.src}`));
  };

  if (firstScript) {
    firstScript.parentNode?.insertBefore(script, firstScript);
  } else {
    document.head.appendChild(script);
  }
}

function loadIFrameApi(): Promise<typeof YT> {
  return new Promise((resolve, reject) => {
    if (typeof window.YT === 'object') {
      resolve(window.YT);
      return;
    }

    load((error) => {
      if (error) reject(error);
    });

    window.onYouTubeIframeAPIReady = () => {
      resolve(window.YT);
    };
  });
}

export { loadIFrameApi };
