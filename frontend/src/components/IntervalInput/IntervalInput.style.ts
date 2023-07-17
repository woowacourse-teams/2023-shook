import { css, styled } from 'styled-components';

export const IntervalContainer = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;
  padding: 0 24px;
  border: 1px solid red;

  font-size: 16px;

  color: white;
`;

export const InputFlex = styled.div`
  display: flex;
`;

export const ErrorMessage = styled.p`
  font-size: 14px;
  color: #f5222d;
`;

export const Separator = styled.span<{ inactive?: boolean }>`
  box-sizing: border-box;
  margin: 0 5px;
  padding-bottom: 8px;
  color: ${({ inactive }) => inactive && '#a7a7a7'};
`;

export const inputBase = css`
  flex: 1;
  margin: 0 5px;

  border: none;
  -webkit-box-shadow: none;
  box-shadow: none;

  margin: 0;
  padding: 0;

  background-color: transparent;
  text-align: center;

  border-bottom: 1px solid white;
  outline: none;
  width: 10px;
`;

export const InputStart = styled.input<{ active: boolean }>`
  ${inputBase}
  color: white;
  border-bottom: 1px solid ${({ active }) => (active ? '#DE2F5F' : 'white')};
`;

export const InputEnd = styled.input`
  ${inputBase}
  color: #a7a7a7;
  border-bottom: 1px solid #a7a7a7;
`;
