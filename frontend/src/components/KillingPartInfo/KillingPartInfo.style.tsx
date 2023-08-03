import { styled } from 'styled-components';
import { Flex } from '../@common';

export const VoteCount = styled.p`
  color: ${({ theme: { color } }) => color.subText};
  font-size: 14px;
  letter-spacing: 1px;
`;

export const RestWrapper = styled.div`
  display: flex;
  align-items: center;
  column-gap: 8px;
`;

export const TimeWrapper = styled.div`
  display: flex;
  justify-content: center;
  font-weight: 700;
  letter-spacing: 1px;
  color: ${({ theme: { color } }) => color.primary};
`;

export const Wrapper = styled(Flex)`
  flex-direction: column;
  color: white;
`;

export const Container = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  border: 1px solid ${({ theme }) => theme.color.secondary};
  margin: 0 8px;
  padding: 16px;
  border-radius: 8px;
`;

export const VoteBox = styled.div`
  display: flex;
  justify-content: center;
`;

export const ShareBox = styled.button`
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.color.white};
  cursor: pointer;
`;

export const Img = styled.img`
  width: 16px;
  height: 16px;
`;
