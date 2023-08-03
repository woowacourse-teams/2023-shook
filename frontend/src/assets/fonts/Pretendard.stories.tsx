import { styled } from 'styled-components';
import type { Meta, StoryObj } from '@storybook/react';

const Pretendard = () => {
  const typo = '타이포그래피: ‘Typography’, 1972. @ㅇ0ㅇ ₩0 〈응♥〉 『힣♪』';

  return (
    <Wrapper>
      <ExtraLight>{typo}</ExtraLight>
      <Light>{typo}</Light>
      <Thin>{typo}</Thin>
      <Regular>{typo}</Regular>
      <Medium>{typo}</Medium>
      <SemiBold>{typo}</SemiBold>
      <Bold>{typo}</Bold>
      <ExtraBold>{typo}</ExtraBold>
      <Black>{typo}</Black>
    </Wrapper>
  );
};

const Wrapper = styled.div`
  font-size: 30px;
  color: white;
`;

const ExtraLight = styled.div`
  font-weight: 100;
`;

const Light = styled.div`
  font-weight: 200;
`;

const Thin = styled.div`
  font-weight: 300;
`;

const Regular = styled.div`
  font-weight: 400;
`;

const Medium = styled.div`
  font-weight: 500;
`;

const SemiBold = styled.div`
  font-weight: 600;
`;

const Bold = styled.div`
  font-weight: 700;
`;

const ExtraBold = styled.div`
  font-weight: 800;
`;

const Black = styled.div`
  font-weight: 900;
`;

const meta: Meta<typeof Pretendard> = {
  component: Pretendard,
};

export default meta;

type Story = StoryObj<typeof Pretendard>;

export const Example: Story = {};
