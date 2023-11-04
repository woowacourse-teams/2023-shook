import styled from 'styled-components';
import ConfirmProvider, { useConfirm } from './ConfirmModalProvider';
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

      const clickModalBtn = async () => {
        const isConfirmed = await confirm({
          title: '제목',
          content: (
            <>
              <p>도밥은 정말 도밥입니까?</p>
              <p>코난은 정말 코난입니까?</p>
            </>
          ),
        });

        console.log('isConfirmed', isConfirmed);

        if (isConfirmed) {
          console.log('confirmed');
          return;
        }

        console.log('denied');
      };

      return (
        <Body>
          <Button onClick={clickModalBtn}>모달 열기</Button>
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
