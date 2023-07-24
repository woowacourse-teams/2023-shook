import { keyframes } from 'styled-components';

export const fadeInBottomToUp = keyframes`  
  0% {
    transform: translateY(80%);
  }
  15% {
    transform: translateY(-10%);
  }
  30% {
    transform: translateY(5%);
  }
  45% {
    transform: translateY(-2%);
  }
  60% {
    transform: translateY(1%);
  }
  75% {
    transform: translateY(-0.5%);
  }
  100% {
    transform: translateY(0);
  }
`;
