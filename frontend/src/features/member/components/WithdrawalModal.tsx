import styled from 'styled-components';
import WITHDRAWAL_MESSAGE from '@/features/member/constants/withdrawalMessage';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';

interface WithdrawalModalProps {
  isOpen: boolean;
  closeModal: () => void;
  onWithdraw: () => void;
}

const WithdrawalModal = ({ isOpen, closeModal, onWithdraw }: WithdrawalModalProps) => {
  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <>
        <ModalContent>{WITHDRAWAL_MESSAGE}</ModalContent>
        <Spacing direction={'vertical'} size={16} />
        <ButtonContainer>
          <ConfirmButton type="button" onClick={onWithdraw}>
            회원 탈퇴
          </ConfirmButton>
          <CancelButton onClick={closeModal} type="button">
            닫기
          </CancelButton>
        </ButtonContainer>
      </>
    </Modal>
  );
};

export default WithdrawalModal;

const ModalContent = styled.div`
  font-size: 16px;
  color: ${({ theme }) => theme.color.subText};

  white-space: pre-line;
  line-height: 200%;
  align-self: start;
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

const CancelButton = styled(Button)`
  flex: 1.5;
  background-color: ${({ theme: { color } }) => color.primary};
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
`;
