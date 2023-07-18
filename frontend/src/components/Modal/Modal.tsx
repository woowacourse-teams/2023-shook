import { useCallback, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { Backdrop, Container, Wrapper } from './Modal.style';
import type { HTMLAttributes, PropsWithChildren, ReactElement } from 'react';

interface ModalProps extends HTMLAttributes<HTMLDivElement> {
  isOpen: boolean;
  closeModal: () => void;
  children: ReactElement | ReactElement[];
}

const Modal = ({ isOpen, closeModal, children }: PropsWithChildren<ModalProps>) => {
  const closeByEsc = useCallback(
    (e: KeyboardEvent) => {
      if (e.key === 'Escape') {
        closeModal();
      }
    },
    [closeModal]
  );

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden';
      window.addEventListener('keydown', closeByEsc);
    }

    return () => {
      document.body.style.overflow = 'auto';
      window.removeEventListener('keydown', closeByEsc);
    };
  }, [isOpen, closeByEsc]);

  return createPortal(
    <Wrapper>
      {isOpen && (
        <>
          <Backdrop onClick={closeModal} />
          <Container>{children}</Container>
        </>
      )}
    </Wrapper>,
    document.body
  );
};

export default Modal;
