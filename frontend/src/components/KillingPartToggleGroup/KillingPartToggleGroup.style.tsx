import { styled } from 'styled-components';

export const ToggleGroup = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;

  width: 100%;
`;

export const ToggleGroupItem = styled.button<{ $active: boolean }>`
  margin: 0;
  padding: 0;
  border: none;
  border-radius: 10px;

  width: 30%;
  height: 30px;

  color: ${({ $active }) => ($active ? '#000000' : '#FFFFFF')};
  background-color: ${({ $active }) => ($active ? '#FFFFFF' : '#434343')};

  cursor: pointer;
`;

export const Spacing = styled.div<{ direction: 'vertical' | 'horizontal'; size: number }>`
  flex: none;
  width: ${({ direction, size }) => (direction === 'horizontal' ? `${size}px` : undefined)};
  height: ${({ direction, size }) => (direction === 'vertical' ? `${size}px` : undefined)};
`;
