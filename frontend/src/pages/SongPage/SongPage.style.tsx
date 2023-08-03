import { styled } from 'styled-components';
import { Flex } from '@/components/@common';

export const Wrapper = styled(Flex)`
  flex-direction: column;
`;

export const SongInfoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;

export const Jacket = styled.img`
  width: 60px;
  height: 60px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 50px;
    height: 50px;
  }
`;

export const Info = styled.div``;

export const SongTitle = styled.p`
  font-size: 24px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

export const Singer = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;

export const SubTitle = styled.h2`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};
`;

export const PrimarySpan = styled.span`
  color: ${({ theme: { color } }) => color.primary};
`;

export const ToggleWrapper = styled.div`
  padding: 8px;
`;

export const SwitchWrapper = styled.div`
  display: flex;
  justify-content: end;
  margin: 0 8px;
  column-gap: 8px;
`;

export const SwitchLabel = styled.label`
  color: ${({ theme: { color } }) => color.white};
  font-size: 14px;
`;
