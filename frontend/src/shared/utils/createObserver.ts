const createObserver = (onIntersecting: () => void, options?: IntersectionObserverInit) => {
  return new IntersectionObserver(([entry]) => {
    if (entry.isIntersecting) {
      onIntersecting();
    }
  }, options);
};

export default createObserver;
