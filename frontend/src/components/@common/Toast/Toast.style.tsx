import { styled } from 'styled-components';

export const Container = styled.div`
  position: relative;
  display: flex;
  align-items: center;
  gap: 4px;
  width: 300px;
  height: 50px;
  padding: 14px;
  border-radius: 60px;
  background: #323232;
`;

export const StatusIcon = styled.img`
  width: 22px;
  height: 22px;
`;

export const Message = styled.span`
  color: #ffffff;
  font-size: 14px;
`;
