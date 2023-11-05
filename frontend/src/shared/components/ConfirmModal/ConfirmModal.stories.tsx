import styled from 'styled-components';
import ConfirmProvider from './ConfirmModalProvider';
import { useConfirm } from './hooks/useConfirm';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof ConfirmProvider> = {
  title: 'shared/Confirm',
  component: ConfirmProvider,
  decorators: [
    (Story) => (
      <ConfirmProvider>
        <Story />
      </ConfirmProvider>
    ),
  ],
};

export default meta;

type Story = StoryObj<typeof ConfirmProvider>;

export const Example: Story = {
  render: () => {
    const RegistrationModal = () => {
      const { confirm } = useConfirm();

      const clickHiByeBtn = async () => {
        const isConfirmed = await confirm({
          title: '하이바이 모달',
          content: (
            <>
              <p>도밥은 정말 도밥입니까?</p>
              <p>코난은 정말 코난입니까?</p>
            </>
          ),
          cancelName: '바이',
          confirmName: '하이',
        });

        if (isConfirmed) {
          alert('confirmed');
          return;
        }

        alert('denied');
      };

      const clickOpenCloseBtn = async () => {
        const isConfirmed = await confirm({
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

    return <RegistrationModal />;
  },
};

const Body = styled.div`
  height: 2400px;
`;

const Button = styled.button`
  color: white;
  border: 2px solid white;
  padding: 4px 11px;
  border-radius: 4px;
`;
