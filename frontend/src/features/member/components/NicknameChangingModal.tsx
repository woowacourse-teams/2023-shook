import styled from 'styled-components';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';

interface NicknameChangingModalProps {
  isOpen: boolean;
  nickname: string | undefined;
  closeModal: () => void;
  onSubmitNickname: () => void;
}

const NicknameChangingModal = ({
  isOpen,
  nickname,
  closeModal,
  onSubmitNickname,
}: NicknameChangingModalProps) => {
  if (!nickname) return;

  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <ModalContent>{`닉네임 변경 시 다시 로그인을 해야합니다.\n닉네임을 변경하시겠습니까?`}</ModalContent>
      <Spacing direction="vertical" size={10} />
      <NicknameContent>{`변경 후 닉네임: ${nickname}`}</NicknameContent>

      <Spacing direction={'vertical'} size={16} />

      <ButtonContainer>
        <CancelButton onClick={closeModal} type="button">
          취소
        </CancelButton>
        <ConfirmButton type="button" onClick={onSubmitNickname}>
          변경
        </ConfirmButton>
      </ButtonContainer>
    </Modal>
  );
};

export default NicknameChangingModal;

const ModalContent = styled.div`
  font-size: 16px;
  line-height: 1.8;
  color: ${({ theme }) => theme.color.subText};
  white-space: pre-line;
`;

const NicknameContent = styled.div`
  color: ${({ theme }) => theme.color.white};
`;

const Button = styled.button`
  height: 36px;
  color: ${({ theme: { color } }) => color.white};
  border-radius: 10px;
`;

const ConfirmButton = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.primary};
`;

const CancelButton = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.secondary};
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
`;
