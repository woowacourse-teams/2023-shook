import useModal from './hooks/useModal';
import Modal from './Modal';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof Modal> = {
  title: 'shared/Modal',
  component: Modal,
};

export default meta;

type Story = StoryObj<typeof Modal>;

export const Example: Story = {
  render: () => {
    const RegistrationModal = () => {
      const { isOpen, openModal, closeModal } = useModal();

      return (
        <>
          <button onClick={openModal}>모달 열기</button>

          <Modal isOpen={isOpen} closeModal={closeModal}>
            <h2>킬링파트에 투표했습니다.</h2>
            <p>로고 색으로 좋아요!</p>
            <div>
              <button type="button" onClick={closeModal}>
                확인
              </button>
              <button type="button">공유하기</button>
            </div>
          </Modal>
        </>
      );
    };

    return <RegistrationModal />;
  },
};
