import React, { createContext, useCallback, useMemo, useRef, useState } from 'react';
import type { Mount, Unmount } from './types';
import type { MutableRefObject, PropsWithChildren, ReactNode } from 'react';

export const OverlayContext = createContext<{
  mount: Mount;
  unmount: Unmount;
  elementIdRef: MutableRefObject<number>;
} | null>(null);

export function OverlayProvider({ children }: PropsWithChildren<{ containerId?: string }>) {
  const [overlayById, setOverlayById] = useState<Map<string, ReactNode>>(new Map());

  const elementIdRef = useRef(1);

  const mount = useCallback<Mount>((id, element) => {
    setOverlayById((overlayById) => {
      const cloned = new Map(overlayById);
      cloned.set(id, element);
      return cloned;
    });
  }, []);

  const unmount = useCallback<Unmount>((id) => {
    setOverlayById((overlayById) => {
      const cloned = new Map(overlayById);
      cloned.delete(id);
      return cloned;
    });
  }, []);

  const context = useMemo(() => ({ mount, unmount, elementIdRef }), [mount, unmount]);

  return (
    <OverlayContext.Provider value={context}>
      {children}
      {[...overlayById.entries()].map(([id, element]) => (
        <React.Fragment key={id}>{element}</React.Fragment>
      ))}
    </OverlayContext.Provider>
  );
}
