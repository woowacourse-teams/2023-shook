import { useCallback, useEffect } from 'react';
import { createPortal } from 'react-dom';
import styled, { keyframes } from 'styled-components';
import type { HTMLAttributes, PropsWithChildren, ReactElement } from 'react';

interface BottomSheet extends HTMLAttributes<HTMLDivElement> {
  isOpen: boolean;
  closeModal: () => void;
  children: ReactElement | ReactElement[];
}

const BottomSheet = ({ isOpen, closeModal, children }: PropsWithChildren<BottomSheet>) => {
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

export default BottomSheet;

export const Backdrop = styled.div`
  position: fixed;
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;

  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;

  background-color: rgba(0, 0, 0, 0.5);
`;

const slideUpAnimation = keyframes`
  from {
    transform: translateY(60svh);
  }
  to {
    transform: translateY(0);
  }
`;

export const Container = styled.div`
  position: fixed;
  bottom: 0;

  overflow-y: scroll;
  display: flex;
  flex-direction: column;

  width: 100%;
  max-width: 800px;
  height: 60svh;
  max-height: 60svh;
  padding: 16px;

  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.secondary};
  border: none;
  border-top-left-radius: 16px;
  border-top-right-radius: 16px;

  animation: ${slideUpAnimation} 1s ease-in-out;
`;

export const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
