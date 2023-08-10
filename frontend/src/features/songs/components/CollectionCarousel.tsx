import { useState } from 'react';
import styled from 'styled-components';
import type { PropsWithChildren } from 'react';
import type React from 'react';

const getChildrenLength = (children: React.ReactNode) => {
  let itemLength = 1;

  if (Array.isArray(children)) {
    itemLength = children.length;
  }

  return itemLength;
};

interface CarouselProps {
  children: React.ReactNode;
}

const CollectionCarousel = ({ children }: CarouselProps) => {
  const [currentItem, setCurrentItem] = useState(0);
  const itemLength = getChildrenLength(children);

  const handleScroll: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollLeft, offsetWidth } = e.target as HTMLDivElement;
    const index = Math.round(scrollLeft / offsetWidth);

    setCurrentItem(index);
  };

  return (
    <>
      <Wrapper>
        <CarouselWrapper onScroll={handleScroll}>{children}</CarouselWrapper>
        <IndicatorWrapper>
          {Array.from({ length: itemLength }, (_, idx) => (
            <Dot key={idx} isActive={idx === currentItem} />
          ))}
        </IndicatorWrapper>
      </Wrapper>
    </>
  );
};

const CarouselItem = ({ children }: PropsWithChildren) => {
  return <Item>{children}</Item>;
};

CollectionCarousel.item = CarouselItem;

export default CollectionCarousel;

const Wrapper = styled.div`
  width: 100%;
  position: relative;
`;

const CarouselWrapper = styled.div`
  display: flex;
  will-change: transform;
  column-gap: 16px;
  margin-bottom: 16px;
  width: 100%;

  overflow-x: scroll;
  scroll-behavior: smooth;
  scroll-snap-type: x mandatory;
  scroll-snap-stop: always;

  & > * {
    scroll-snap-align: center;
    scroll-snap-stop: always;
  }

  &::-webkit-scrollbar {
    display: none;
  }
`;

const IndicatorWrapper = styled.div`
  display: flex;
  justify-content: center;
  background-color: transparent;
  column-gap: 8px;
`;

const Dot = styled.div<{ isActive: boolean }>`
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: ${({ isActive, theme: { color } }) =>
    isActive ? color.primary : color.secondary};
`;

const Item = styled.li`
  flex: 0 0 auto;
  z-index: -1;
  width: 100%;
  list-style-type: none;

  height: 140px;
  color: ${({ theme }) => theme.color.white};

  background-color: ${({ theme }) => theme.color.secondary};
  border-radius: 8px;

  transition: 1s all ease;
  display: flex;
  justify-content: center;
  text-align: center;
  padding: 16px;
`;
