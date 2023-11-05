/* eslint-disable react-hooks/exhaustive-deps */
import { createContext, useCallback, useEffect, useState } from 'react';
import { createPortal } from 'react-dom';
import ConfirmModal from './ConfirmModal';
import type { ReactNode } from 'react';

export const ConfirmContext = createContext<null | {
  confirm: (modalState: ModalState) => Promise<boolean>;
}>(null);

interface ModalState {
  title: string;
  content: ReactNode;
  cancelName?: string;
  confirmName?: string;
}

const ConfirmProvider = ({ children }: { children: ReactNode }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [resolver, setResolver] = useState<{
    resolve: (value: boolean | PromiseLike<boolean>) => void;
  } | null>(null);
  const [modalState, setModalState] = useState<ModalState>({
    title: '',
    content: '',
    cancelName: '닫기',
    confirmName: '확인',
  });
  const { title, content, cancelName, confirmName } = modalState;

  const confirm = async (modal: ModalState) => {
    openModal();
    setModalState(modal);

    const promise = await new Promise<boolean>((resolve) => {
      setResolver({ resolve });
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
    if (!resolver) return;

    resolver.resolve(status);
  };

  const onCancel = useCallback(() => {
    resolveConfirmation(false);
    closeModal();
  }, []);

  const onConfirm = useCallback(() => {
    resolveConfirmation(true);
    closeModal();
  }, []);

  const onKeyDown = useCallback((event: KeyboardEvent) => {
    const { key } = event;
    if (key === 'Enter') {
      event.preventDefault();
      resolveConfirmation(false);
      closeModal();
    }

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
  }, []);

  return (
    <ConfirmContext.Provider value={{ confirm }}>
      {children}
      {isOpen &&
        createPortal(
          <ConfirmModal
            title={title}
            content={content}
            cancelName={cancelName ?? '닫기'}
            confirmName={confirmName ?? '확인'}
            onCancel={onCancel}
            onConfirm={onConfirm}
          />,
          document.body
        )}
    </ConfirmContext.Provider>
  );
};

export default ConfirmProvider;
