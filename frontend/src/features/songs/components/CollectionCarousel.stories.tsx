import { styled } from 'styled-components';
import jacket from '@/assets/icon/album-jacket-default.svg';
import Spacing from '@/shared/components/Spacing';
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
    <>
      <CollectionCarousel>
        {Array.from({ length: 3 }, (_, idx) => {
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
      <Spacing direction="vertical" size={10} />
      <CollectionCarousel>
        {Array.from({ length: 10 }, (_, idx) => {
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
    </>
  ),
};

const Jacket = styled.img`
  grid-area: jacket;
`;
const Singer = styled.div`
  grid-area: singer;
  padding-left: 16px;
`;
const Title = styled.div`
  grid-area: title;
  padding-left: 16px;
`;
const Collections = styled.div`
  grid-area: collections;
  padding-left: 16px;
`;

const Container = styled.div`
  width: 400px;
  height: 800px;
  padding: 16px;
  background-color: black;
`;

const Content = styled.div`
  display: grid;
  grid-template-areas:
    'jacket title'
    'jacket singer'
    'jacket collections';
  grid-template-columns: 1fr 2fr;
  grid-template-rows: repeat(3, 30px);

  width: 100px;
  margin: auto;
  padding: 16px;

  text-align: left;
`;
