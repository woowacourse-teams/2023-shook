import { useContext, useEffect, useMemo, useRef } from 'react';
import { OverlayController } from './OverlayController';
import { OverlayContext } from './OverlayProvider';
import type { OverlayControlRef } from './OverlayController';
import type { CreateOverlayElement } from './types';

interface Options {
  exitOnUnmount?: boolean;
}

export function useOverlay({ exitOnUnmount = true }: Options = {}) {
  const context = useContext(OverlayContext);

  if (context === null) {
    throw new Error('useOverlay는 OverlayProvider 내부에서 사용 가능합니다.');
  }

  const { mount, unmount, elementIdRef } = context;

  const id = String(elementIdRef.current++);
  const overlayRef = useRef<OverlayControlRef>(null);

  useEffect(() => {
    return () => {
      if (exitOnUnmount) {
        unmount(id);
      }
    };
  }, [exitOnUnmount, id, unmount]);

  return useMemo(
    () => ({
      open: (overlayElement: CreateOverlayElement) => {
        mount(
          id,
          <OverlayController
            // NOTE: 오버레이를 열때마다 state를 초기화하기 위함입니다.
            key={Date.now()}
            ref={overlayRef}
            overlayElement={overlayElement}
            onExit={() => unmount(id)}
          />
        );
      },
      close: () => {
        overlayRef.current?.close();
      },
      exit: () => {
        unmount(id);
      },
    }),
    [id, mount, unmount]
  );
}
