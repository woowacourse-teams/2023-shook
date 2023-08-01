import { styled } from 'styled-components';

export const Container = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
`;

export const Item = styled.button<{ $active: boolean }>`
  flex: 1;
  margin: 0;
  padding: 0;
  border: none;
  border-radius: 10px;
  min-width: 50px;
  height: 30px;
  font-weight: ${({ $active }) => ($active ? '700' : '500')};
  color: ${({ $active, theme: { color } }) => ($active ? color.black : color.white)};
  background-color: ${({ $active, theme: { color } }) => ($active ? color.white : color.secondary)};
  cursor: pointer;
`;
