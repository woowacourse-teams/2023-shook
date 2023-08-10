import { useRef, useState } from 'react';
import styled from 'styled-components';
import Spacing from '@/shared/components/Spacing';

const Carousel = () => {
  const [currentIndex, setCurrentIndex] = useState(0);
  const ref = useRef<HTMLDivElement>(null);
  const handleScroll: React.UIEventHandler<HTMLDivElement> = (e) => {
    const { scrollLeft, offsetWidth } = e.target as HTMLDivElement;
    console.log(offsetWidth);
    console.log(scrollLeft);

    const index = Math.round(scrollLeft / offsetWidth);
    console.log(index);
    setCurrentIndex(index);
  };

  return (
    <>
      <Wrapper>
        <CarouselWrapper onScroll={handleScroll}>
          {Array.from({ length: 10 }, (_, idx) => {
            return (
              <Item ref={ref} key={idx}>
                {idx}
              </Item>
            );
          })}
        </CarouselWrapper>
        <Spacing direction="vertical" size={8} />
        <IndicatorWrapper>
          {Array.from({ length: 10 }, (_, idx) => (
            <Dot key={idx} isActive={idx === currentIndex} />
          ))}
        </IndicatorWrapper>
      </Wrapper>
    </>
  );
};
export default Carousel;

const Wrapper = styled.div`
  width: 100%;
  position: relative;
`;

const CarouselWrapper = styled.div`
  display: flex;
  will-change: transform;
  column-gap: 16px;

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

const Item = styled.div`
  flex: 0 0 auto;
  z-index: -1;
  width: 100%;

  height: 140px;
  color: black;

  background-color: white;
  border-radius: 8px;

  font-size: 24px;
  transition: 1s all ease;
`;

const IndicatorWrapper = styled.div`
  display: flex;
  justify-content: center;

  z-index: 1;

  background-color: transparent;
  padding: 4px;
  border-radius: 4px;
`;

const Dot = styled.div<{ isActive: boolean }>`
  width: 8px;
  height: 8px;
  border-radius: 50%;
  background-color: ${({ isActive, theme: { color } }) =>
    isActive ? color.primary : color.disabledBackground};
  margin: 0 3px;
`;
