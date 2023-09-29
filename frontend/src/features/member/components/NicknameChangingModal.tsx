import styled from 'styled-components';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';

interface NicknameChangingModalProps {
  isOpen: boolean;
  nickname: string | undefined;
  closeModal: () => void;
  onChangeNickname: () => void;
}

const NicknameChangingModal = ({
  isOpen,
  nickname,
  closeModal,
  onChangeNickname,
}: NicknameChangingModalProps) => {
  if (!nickname) return;

  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <>
        <ModalContent>{`닉네임을 ${nickname}로 정말 바꾸겠습니까?`}</ModalContent>
        <Spacing direction={'vertical'} size={16} />
        <ButtonContainer>
          <CancelButton onClick={closeModal} type="button">
            취소
          </CancelButton>
          <ConfirmButton type="button" onClick={onChangeNickname}>
            변경
          </ConfirmButton>
        </ButtonContainer>
      </>
    </Modal>
  );
};

export default NicknameChangingModal;

const ModalContent = styled.div`
  align-self: start;

  font-size: 16px;
  line-height: 200%;
  color: ${({ theme }) => theme.color.subText};
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
