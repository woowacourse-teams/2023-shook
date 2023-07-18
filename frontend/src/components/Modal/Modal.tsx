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
    ({ key }: KeyboardEvent) => {
      if (key === 'Escape') {
        closeModal();
      }
    },
    [closeModal]
  );

  useEffect(() => {
    if (isOpen) {
      document.body.style.overflow = 'hidden';
      document.addEventListener('keydown', closeByEsc);
    }

    return () => {
      document.body.style.overflow = 'auto';
      document.removeEventListener('keydown', closeByEsc);
    };
  }, [isOpen, closeByEsc]);

  return createPortal(
    <Wrapper>
      {isOpen && (
        <>
          <Backdrop role="backdrop" onClick={closeModal} aria-hidden="true" />
          <Container role="dialog">{children}</Container>
        </>
      )}
    </Wrapper>,
    document.body
  );
};

export default Modal;
