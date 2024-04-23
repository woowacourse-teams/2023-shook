import type { ReactNode } from 'react';

export type Mount = (id: string, element: ReactNode) => void;
export type Unmount = (id: string) => void;

export type CreateOverlayElement = (props: {
  isOpen: boolean;
  close: () => void;
  exit: () => void;
}) => ReactNode;
