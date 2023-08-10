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
  const [currentIndex, setCurrentIndex] = useState(0);
  const itemLength = getChildrenLength(children);

  const handleScroll: React.UIEventHandler<HTMLUListElement> = (event) => {
    const { scrollLeft, offsetWidth } = event.target as HTMLUListElement;
    const index = Math.round(scrollLeft / offsetWidth);

    setCurrentIndex(index);
  };

  return (
    <>
      <Wrapper>
        <CarouselWrapper onScroll={handleScroll}>{children}</CarouselWrapper>
        <IndicatorWrapper aria-hidden>
          {Array.from({ length: itemLength }, (_, idx) => (
            <Dot key={idx} isActive={idx === currentIndex} />
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

const CarouselWrapper = styled.ul`
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
