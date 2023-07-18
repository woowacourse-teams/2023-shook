import { css, styled } from 'styled-components';

export const IntervalContainer = styled.div`
  display: flex;
  flex-direction: column;

  justify-content: space-between;
  padding: 0 24px;

  font-size: 16px;

  color: #ffffff;
`;

export const Flex = styled.div`
  display: flex;
`;

export const ErrorMessage = styled.p`
  font-size: 12px;
  margin: 8px 0;
  color: #f5222d;
`;

export const Separator = styled.span<{ $inactive?: boolean }>`
  flex: none;
  text-align: center;
  margin: 0 8px;
  padding-bottom: 8px;
  color: ${({ $inactive }) => $inactive && '#a7a7a7'};
`;

export const inputBase = css`
  flex: 1;
  margin: 0 8px;

  border: none;
  -webkit-box-shadow: none;
  box-shadow: none;

  margin: 0;
  padding: 0;

  background-color: transparent;
  text-align: center;

  border-bottom: 1px solid white;
  outline: none;
  width: 16px;
`;

export const InputStart = styled.input<{ $active: boolean }>`
  ${inputBase}
  color: ${({ $active }) => ($active ? '#de2f5f' : '#ffffff')};
  border-bottom: 1px solid ${({ $active }) => ($active ? '#de2f5f' : '#ffffff')};
`;

export const InputEnd = styled.input`
  ${inputBase}
  color: #a7a7a7;
  border-bottom: 1px solid #a7a7a7;
`;

export const Label = styled.label`
  display: none;
`;
