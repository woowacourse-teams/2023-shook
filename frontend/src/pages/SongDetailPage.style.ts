import { styled } from 'styled-components';

export const ButtonContainer = styled.div`
  display: flex;
  width: 100%;
  gap: 16px;
`;

export const Container = styled.section`
  display: flex;
  width: 100%;
  flex-direction: column;
  gap: 20px;
`;

export const SongInfoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
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
