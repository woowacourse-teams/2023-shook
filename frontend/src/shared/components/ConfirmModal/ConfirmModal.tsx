import { createPortal } from 'react-dom';
import { Flex } from 'shook-layout';
import styled, { css } from 'styled-components';
import Spacing from '../Spacing';
import type { ReactNode } from 'react';

interface ConfirmModalProps {
  title: string;
  content: ReactNode;
  denial: string;
  confirmation: string;
  onDeny: () => void;
  onConfirm: () => void;
}

const ConfirmModal = ({
  title,
  content,
  denial,
  confirmation,
  onDeny,
  onConfirm,
}: ConfirmModalProps) => {
  return createPortal(
    <>
      <Backdrop role="dialog" aria-modal="true" />
      <Container>
        <Title>{title}</Title>
        <Spacing direction="vertical" size={10} />
        <Content>{content}</Content>
        <Spacing direction="vertical" size={10} />
        <ButtonFlex $gap={16}>
          <CancelButton onClick={onDeny}>{denial}</CancelButton>
          <ConfirmButton onClick={onConfirm}>{confirmation}</ConfirmButton>
        </ButtonFlex>
      </Container>
    </>,
    document.body
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
  font-size: 18px;
  text-align: left;
`;

const Content = styled.div``;

const buttonStyle = css`
  flex: 1;

  width: 100%;
  height: 36px;

  color: ${({ theme: { color } }) => color.white};

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
