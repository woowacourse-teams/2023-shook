import { styled } from 'styled-components';

export const ToggleGroup = styled.div`
  display: flex;
  flex-direction: row;

  width: 100%;
`;

export const ToggleGroupItem = styled.button<{ active: boolean }>`
  margin: 0;
  padding: 0;
  border: none;
  border-radius: 10px;

  width: 100px;
  height: 30px;

  color: ${(props) => (props.active ? '#000000' : '#FFFFFF')};
  background-color: ${(props) => (props.active ? '#FFFFFF' : '#434343')};

  cursor: pointer;
`;

export const Spacing = styled.div<{ direction: 'vertical' | 'horizontal'; size: number }>`
  flex: none;
  width: ${(props) => (props.direction === 'horizontal' ? `${props.size}px` : undefined)};
  height: ${(props) => (props.direction === 'vertical' ? `${props.size}px` : undefined)};
`;
