/* eslint-disable react-hooks/exhaustive-deps */
import { createContext, useCallback, useEffect, useState, useRef } from 'react';
import { createPortal } from 'react-dom';
import ConfirmModal from './ConfirmModal';
import type { ReactNode } from 'react';

export const ConfirmContext = createContext<null | {
  confirmPopup: (modalState: ModalContents) => Promise<boolean>;
}>(null);

interface ModalContents {
  title: string;
  content: ReactNode;
  denial?: string;
  confirmation?: string;
}

const ConfirmModalProvider = ({ children }: { children: ReactNode }) => {
  const [isOpen, setIsOpen] = useState(false);
  const resolverRef = useRef<{
    resolve: (value: boolean) => void;
  } | null>(null);
  const [modalContents, setModalContents] = useState<ModalContents>({
    title: '',
    content: '',
    denial: '닫기',
    confirmation: '확인',
  });
  const { title, content, denial, confirmation } = modalContents;

  // ContextAPI를 통해 confirm 함수만 제공합니다.
  const confirmPopup = (contents: ModalContents) => {
    openModal();
    setModalContents(contents);

    const promise = new Promise<boolean>((resolve) => {
      resolverRef.current = { resolve };
    });

    return promise;
  };

  const closeModal = () => {
    setIsOpen(false);
  };

  const openModal = () => {
    setIsOpen(true);
  };

  const resolveConfirmation = (status: boolean) => {
    if (resolverRef?.current) {
      resolverRef.current.resolve(status);
    }
  };

  const onDeny = useCallback(() => {
    resolveConfirmation(false);
    closeModal();
  }, []);

  const onConfirm = useCallback(() => {
    resolveConfirmation(true);
    closeModal();
  }, []);

  const onKeyDown = useCallback(({ key }: KeyboardEvent) => {
    if (key === 'Escape') {
      resolveConfirmation(false);
      closeModal();
    }
  }, []);

  useEffect(() => {
    if (isOpen) {
      document.addEventListener('keydown', onKeyDown);
      document.body.style.overflow = 'hidden';
    }

    return () => {
      document.removeEventListener('keydown', onKeyDown);
      document.body.style.overflow = 'auto';
    };
  }, [isOpen]);

  return (
    <ConfirmContext.Provider value={{ confirmPopup }}>
      {children}
      {isOpen &&
        createPortal(
          <ConfirmModal
            title={title}
            content={content}
            denial={denial ?? '닫기'}
            confirmation={confirmation ?? '확인'}
            onDeny={onDeny}
            onConfirm={onConfirm}
          />,
          document.body
        )}
    </ConfirmContext.Provider>
  );
};

export default ConfirmModalProvider;
