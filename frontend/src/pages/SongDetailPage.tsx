import useModal from '@/components/Modal/hooks/useModal';
import Modal from '@/components/Modal/Modal';
import {
  Confirm,
  Flex,
  ModalContent,
  ModalTitle,
  Register,
  Share,
  Spacing,
} from './SongDetailPage.style';

const SongDetailPage = () => {
  const { isOpen, openModal, closeModal } = useModal({ defaultOpen: false });

  const submitKillingPart = () => {
    openModal();
  };

  return (
    <div>
      <Register type="button" onClick={submitKillingPart}>
        등록
      </Register>

      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트에 투표했습니다.</ModalTitle>
        <ModalContent>로고 색으로 좋아요!</ModalContent>
        <Flex>
          <Confirm type="button" onClick={closeModal}>
            확인
          </Confirm>
          <Spacing direction="horizontal" size={12} />
          <Share type="button">공유하기</Share>
        </Flex>
      </Modal>
    </div>
  );
};

export default SongDetailPage;
