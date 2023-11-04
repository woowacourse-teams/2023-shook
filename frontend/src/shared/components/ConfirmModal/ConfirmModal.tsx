import { useEffect } from 'react';
import { Flex } from 'shook-layout';
import styled, { css } from 'styled-components';
import Spacing from '../Spacing';
import type { ReactNode } from 'react';

interface ConfirmModalProps {
  title: string;
  content: ReactNode;
  cancelName: string;
  confirmName: string;
  onCancel: () => void;
  onConfirm: () => void;
  onKeyDown: (event: KeyboardEvent) => void;
}

const ConfirmModal = ({
  title,
  content,
  cancelName,
  confirmName,
  onCancel,
  onConfirm,
  onKeyDown,
}: ConfirmModalProps) => {
  useEffect(() => {
    document.addEventListener('keydown', onKeyDown);
    document.body.style.overflow = 'hidden';

    return () => {
      document.removeEventListener('keydown', onKeyDown);
      document.body.style.overflow = 'auto';
    };
  }, []);

  return (
    <>
      <Backdrop role="dialog" aria-modal="true" />
      <Container>
        <Title>{title}</Title>
        <Spacing direction="vertical" size={10} />
        <Content>{content}</Content>
        <Spacing direction="vertical" size={10} />
        <ButtonFlex $gap={16}>
          <CancelButton onClick={onCancel}>{cancelName}</CancelButton>
          <ConfirmButton onClick={onConfirm}>{confirmName}</ConfirmButton>
        </ButtonFlex>
      </Container>
    </>
  );
};

export default ConfirmModal;

const Backdrop = styled.div`
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

const Container = styled.section`
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);

  min-width: 300px;
  margin: 0 auto;
  padding: 24px;

  color: #ffffff;

  background-color: #17171c;
  border: none;
  border-radius: 16px;
`;
const ButtonFlex = styled(Flex)`
  width: 100%;
`;
const Title = styled.header`
  text-align: left;
  font-size: 18px;
`;
const Content = styled.div``;

const buttonStyle = css`
  flex: 1;
  height: 36px;
  color: ${({ theme: { color } }) => color.white};
  width: 100%;

  border-radius: 10px;
`;

const CancelButton = styled.button`
  background-color: ${({ theme: { color } }) => color.secondary};
  ${buttonStyle}
`;
const ConfirmButton = styled.button`
  background-color: ${({ theme: { color } }) => color.primary};
  ${buttonStyle}
`;
