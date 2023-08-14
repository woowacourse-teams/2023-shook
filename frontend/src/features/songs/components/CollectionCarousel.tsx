import { Children, useEffect, useRef, useState } from 'react';
import styled from 'styled-components';
import type { ReactElement } from 'react';

interface CarouselProps {
  children: ReactElement | ReactElement[];
}

const CollectionCarousel = ({ children }: CarouselProps) => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const carouselRef = useRef<HTMLUListElement | null>(null);
  const numberOfItems = Children.count(children);

  const handleScroll: React.UIEventHandler<HTMLUListElement> = (event) => {
    const { scrollLeft, offsetWidth } = event.target as HTMLUListElement;
    const index = Math.round(scrollLeft / offsetWidth);

    setCurrentIndex(index);
  };

  useEffect(() => {
    const interval = setInterval(() => {
      if (carouselRef.current) {
        const nextIndex = (currentIndex + 1) % numberOfItems;
        carouselRef.current.scrollLeft = nextIndex * carouselRef.current.offsetWidth;
      }
    }, 2000);

    return () => clearInterval(interval);
  }, [currentIndex, numberOfItems]);

  return (
    <>
      <Wrapper>
        <CarouselWrapper ref={carouselRef} onScroll={handleScroll}>
          {children}
        </CarouselWrapper>
        <IndicatorWrapper aria-hidden>
          {Array.from({ length: numberOfItems }, (_, idx) => (
            <Dot key={idx} isActive={idx === currentIndex} />
          ))}
        </IndicatorWrapper>
      </Wrapper>
    </>
  );
};

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

  & > li {
    scroll-snap-align: center;
    scroll-snap-stop: always;

    width: 100% !important;
    flex: 0 0 auto;

    height: 140px;
    color: ${({ theme }) => theme.color.white};

    background-color: ${({ theme }) => theme.color.secondary};
    border-radius: 8px;
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
