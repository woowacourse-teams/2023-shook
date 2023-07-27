import { styled } from 'styled-components';

export const ToggleGroup = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  gap: 20px;
`;

export const ToggleGroupItem = styled.button<{ $active: boolean }>`
  flex: 1;
  margin: 0;
  padding: 0;
  border: none;
  border-radius: 10px;
  min-width: 50px;
  height: 30px;
  color: ${({ $active }) => ($active ? '#000000' : '#FFFFFF')};
  background-color: ${({ $active }) => ($active ? '#FFFFFF' : '#434343')};
  cursor: pointer;
`;
