import { Link } from 'react-router-dom';

import { styled } from 'styled-components';
import Modal from '@/shared/components/Modal/Modal';
import ROUTE_PATH from '@/shared/constants/path';

interface LoginModalProps {
  isOpen: boolean;
  closeModal: () => void;
  messageList: string[];
}

const LoginModal = ({ isOpen, closeModal, messageList }: LoginModalProps) => {
  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <ModalTitle>로그인이 필요합니다</ModalTitle>
      <>
        {messageList.map((message) => {
          return <ModalContent key={message}>{message}</ModalContent>;
        })}
      </>
      <ButtonContainer>
        <ConfirmButton type="button" onClick={closeModal}>
          닫기
        </ConfirmButton>
        <FlexLink to={ROUTE_PATH.LOGIN}>
          <LoginButton type="button">로그인하러 가기</LoginButton>
        </FlexLink>
      </ButtonContainer>
    </Modal>
  );
};

export default LoginModal;

const ModalTitle = styled.h3``;

const ModalContent = styled.div`
  margin: 16px 0;

  font-size: 16px;
  color: #b5b3bc;
  text-align: center;
  white-space: pre-line;
`;

const Button = styled.button`
  cursor: pointer;

  height: 36px;

  color: ${({ theme: { color } }) => color.white};

  border: none;
  border-radius: 10px;
`;

const ConfirmButton = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.secondary};
`;

const LoginButton = styled(Button)`
  width: 100%;
  background-color: ${({ theme: { color } }) => color.primary};
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
`;

const FlexLink = styled(Link)`
  flex: 1.5;
`;
