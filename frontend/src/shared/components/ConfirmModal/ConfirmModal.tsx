import { Flex } from 'shook-layout';
import styled, { css } from 'styled-components';
import Modal from '../Modal/Modal';
import Spacing from '../Spacing';
import type { ReactNode } from 'react';

interface ConfirmModalProps {
  isOpen: boolean;
  closeModal: () => void;
  title: string;
  content: ReactNode;
  denial: string;
  confirmation: string;
  onDeny: () => void;
  onConfirm: () => void;
}

const ConfirmModal = ({
  isOpen,
  closeModal,
  title,
  content,
  denial,
  confirmation,
  onDeny,
  onConfirm,
}: ConfirmModalProps) => {
  const focusTitle: React.RefCallback<HTMLDivElement> = (dom) => {
    dom && dom.focus();
  };

  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <Title ref={focusTitle} tabIndex={0}>
        {title}
      </Title>
      <Spacing direction="vertical" size={10} />
      <Content>{content}</Content>
      <Spacing direction="vertical" size={10} />
      <ButtonFlex $gap={16}>
        <DenialButton type="button" onClick={onDeny}>
          {denial}
        </DenialButton>
        <ConfirmButton type="button" onClick={onConfirm}>
          {confirmation}
        </ConfirmButton>
      </ButtonFlex>
    </Modal>
  );
};

export default ConfirmModal;

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

const DenialButton = styled.button`
  background-color: ${({ theme: { color } }) => color.secondary};
  ${buttonStyle}
`;

const ConfirmButton = styled.button`
  background-color: ${({ theme: { color } }) => color.primary};
  ${buttonStyle}
`;
