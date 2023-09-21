import { useCallback, useEffect } from 'react';
import { createPortal } from 'react-dom';
import { styled } from 'styled-components';
import type { HTMLAttributes, PropsWithChildren, ReactElement } from 'react';
import type { CSSProp } from 'styled-components';

interface ModalProps extends HTMLAttributes<HTMLDivElement> {
  isOpen: boolean;
  closeModal: () => void;
  children: ReactElement | ReactElement[];
  css?: CSSProp;
  canCloseByBackDrop?: boolean;
}

const Modal = ({
  canCloseByBackDrop = true,
  isOpen,
  closeModal,
  children,
  css,
}: PropsWithChildren<ModalProps>) => {
  const closeByEsc = useCallback(
    ({ key }: KeyboardEvent) => {
      if (key === 'Escape') {
        closeModal();
      }
    },
    [closeModal]
  );

  const closeModalByBackDrop = () => {
    if (!canCloseByBackDrop) return;

    closeModal();
  };

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
    <>
      {isOpen && (
        <Wrapper>
          <Backdrop role="backdrop" onClick={closeModalByBackDrop} aria-hidden="true" />
          <Container role="dialog" $css={css}>
            {children}
          </Container>
        </Wrapper>
      )}
    </>,
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

  width: 100%;
  height: 100%;
  margin: 0;
  padding: 0;

  background-color: rgba(0, 0, 0, 0.7);
`;

export const Container = styled.div<{ $css: CSSProp }>`
  position: fixed;
  top: 50%;
  transform: translateY(-50%);

  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  min-width: 300px;
  margin: 0 auto;
  padding: 24px;

  color: #ffffff;

  background-color: #17171c;
  border: none;
  border-radius: 16px;

  ${(props) => props.$css}
`;

export const Wrapper = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
