import { styled } from 'styled-components';
import jacket from '@/assets/icon/album-jacket-default.svg';
import CollectionCarousel from './CollectionCarousel';
import type { Meta, StoryObj } from '@storybook/react';

const meta: Meta<typeof CollectionCarousel> = {
  component: CollectionCarousel,
  title: 'CollectionCarousel',
  decorators: [
    (Story) => (
      <Container>
        <Story />
      </Container>
    ),
  ],
};

export default meta;

type Story = StoryObj<typeof CollectionCarousel>;

const data = {
  albumJacket: jacket,
  singer: 'Taylor Swift',
  title: 'Shake it off',
};

export const Default: Story = {
  render: () => (
    <CollectionCarousel>
      {Array.from({ length: 1 }, (_, idx) => {
        return (
          <Content key={idx}>
            <Jacket src={data.albumJacket} />
            <Singer>{data.title}</Singer>
            <Title>{data.singer}</Title>
            <Collections>{'2,412명이 참여중'}</Collections>
          </Content>
        );
      })}
    </CollectionCarousel>
  ),
};

const Jacket = styled.img`
  grid-area: jacket;
`;
const Singer = styled.div`
  padding-left: 16px;
  grid-area: singer;
`;
const Title = styled.div`
  padding-left: 16px;
  grid-area: title;
`;
const Collections = styled.div`
  padding-left: 16px;
  grid-area: collections;
`;

const Container = styled.div`
  background-color: black;
  padding: 16px;
  width: 400px;
  height: 800px;
`;

const Content = styled.li`
  margin: auto;
  display: grid;
  grid-template-rows: repeat(3, 30px);
  grid-template-columns: 1fr 2fr;
  grid-template-areas:
    'jacket title'
    'jacket singer'
    'jacket collections';

  text-align: left;
`;
