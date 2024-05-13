import styled from 'styled-components';
import { useConfirmModal } from './hooks/useConfirmModal';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta = {
  title: 'shared/ConfirmModal',
};

export default meta;

type Story = StoryObj;

export const Example: Story = {
  render: () => {
    const Modal = () => {
      const { openConfirmModal } = useConfirmModal();

      const clickHiByeBtn = async () => {
        const isConfirmed = await openConfirmModal({
          title: '하이바이 모달',
          content: (
            <>
              <p>도밥은 정말 도밥입니까?</p>
              <p>코난은 정말 코난입니까?</p>
            </>
          ),
          denial: '바이',
          confirmation: '하이',
        });

        if (isConfirmed) {
          alert('confirmed');
          return;
        }

        alert('denied');
      };

      // denial과 confirmation 기본값은 '닫기'와 '확인'입니다.
      const clickOpenCloseBtn = async () => {
        const isConfirmed = await openConfirmModal({
          title: '오쁜클로즈 모달',
          content: (
            <>
              <p>코난은 정말 코난입니까?</p>
              <p>도밥은 정말 도밥입니까?</p>
            </>
          ),
        });

        if (isConfirmed) {
          alert('confirmed');
          return;
        }

        alert('denied');
      };

      return (
        <Body>
          <Button onClick={clickHiByeBtn}>하이바이 모달열기</Button>
          <Button onClick={clickOpenCloseBtn}>닫기확인 모달열기</Button>
        </Body>
      );
    };

    return <Modal />;
  },
};

const Body = styled.div`
  height: 2400px;
`;

const Button = styled.button`
  padding: 4px 11px;
  color: white;
  border: 2px solid white;
  border-radius: 4px;
`;
