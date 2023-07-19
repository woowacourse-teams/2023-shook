import { styled } from 'styled-components';

export const SliderWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const Slider = styled.input<{ interval: number }>`
  width: 360px;
  height: 40px;

  -webkit-appearance: none;
  background: transparent;

  cursor: pointer;

  &:active {
    cursor: grabbing;
  }

  &:focus {
    outline: none;
  }

  // TODO: webkit, moz cross browsing
  // https://css-tricks.com/styling-cross-browser-compatible-range-inputs-css/
  &::-webkit-slider-runnable-track {
    width: 100%;
    height: 8px;
    background-color: gray;
    border: none;
    border-radius: 5px;
  }

  &::-webkit-slider-thumb {
    position: relative;
    -webkit-appearance: none;
    top: -4px;
    width: ${({ interval }) => interval * 6}px;
    height: 16px;
    background-color: #de2f5f;
    border: none;
    border-radius: 20px;
  }
`;
