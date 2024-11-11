export const loadScript = (source: string): Promise<void> => {
  const element = document.querySelector(`script[src="${source}"]`);

  if (element) {
    return Promise.resolve();
  }

  return new Promise((resolve) => {
    const script = document.createElement('script');

    script.async = true;
    script.defer = true;
    script.src = source;
    script.onload = () => resolve();

    document.body.appendChild(script);
  });
};
