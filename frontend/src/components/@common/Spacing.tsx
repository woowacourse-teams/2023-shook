import { styled } from 'styled-components';

const Spacing = styled.div<{ direction: 'horizontal' | 'vertical'; size: number }>`
  flex: none;
  min-width: ${({ direction, size }) => (direction === 'horizontal' ? `${size}px` : '0')};
  min-height: ${({ direction, size }) => (direction === 'vertical' ? `${size}px` : '0')};
`;

export default Spacing;
