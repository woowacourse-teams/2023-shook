import { useCallback, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';
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

export const Backdrop = styled.div`
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  padding: 0;
  margin: 0;

  width: 100%;
  height: 100%;
  background-color: rgba(0, 0, 0, 0.5);
`;

export const Container = styled.div`
  position: fixed;
  top: 50%;
  transform: translateY(-50%);

  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  min-width: 300px;

  margin: 0 auto;
  padding: 24px;

  border: none;
  border-radius: 16px;

  background-color: #17171c;
  color: #ffffff;
`;

export const Wrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;
