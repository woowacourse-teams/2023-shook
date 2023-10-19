import { styled } from 'styled-components';
import useModal from '../Modal/hooks/useModal';
import BottomSheet from './BottomSheet';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof BottomSheet> = {
  title: 'shared/BottomSheet',
  component: BottomSheet,
};

export default meta;

type Story = StoryObj<typeof BottomSheet>;

export const Example: Story = {
  render: () => {
    const RegistrationModal = () => {
      const { isOpen, openModal, closeModal } = useModal();

      return (
        <>
          <button onClick={openModal}>모달 열기</button>

          <BottomSheet isOpen={isOpen} closeModal={closeModal}>
            <Continer>
              <div>댓글 10개</div>
              <ul>
                <li>대충 댓글이라는 댓글이라는 댓글이라는....</li>
                <li>대충 댓글이라는 댓글이라는 댓글이라는....</li>
                <li>대충 댓글이라는 댓글이라는 댓글이라는....</li>
              </ul>
            </Continer>
          </BottomSheet>
        </>
      );
    };

    return <RegistrationModal />;
  },
};

const Continer = styled.div`
  padding: 16px;
`;
