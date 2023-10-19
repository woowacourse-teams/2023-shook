import styled from 'styled-components';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';

interface MyPartModalProps {
  isOpen: boolean;
  closeModal: () => void;
  onDelete: () => void;
}

const MyPartModal = ({ isOpen, closeModal, onDelete }: MyPartModalProps) => {
  return (
    <Modal isOpen={isOpen} closeModal={closeModal}>
      <ModalTitle>정말 삭제하시겠습니까?</ModalTitle>
      <Spacing direction="vertical" size={24} />
      <ButtonContainer>
        <CancelButton type="button" onClick={closeModal}>
          취소
        </CancelButton>
        <DeleteButton type="button" onClick={onDelete}>
          삭제
        </DeleteButton>
      </ButtonContainer>
    </Modal>
  );
};

const ModalTitle = styled.h3``;

const Button = styled.button`
  cursor: pointer;

  height: 36px;

  color: ${({ theme: { color } }) => color.white};

  border: none;
  border-radius: 10px;
`;

const CancelButton = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.secondary};
`;

const DeleteButton = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.primary};
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
`;

export default MyPartModal;
