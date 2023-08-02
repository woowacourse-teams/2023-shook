import { Link } from 'react-router-dom';
import { styled } from 'styled-components';

export const StyledLink = styled(Link)`
  width: 100%;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

export const Title = styled.h2`
  align-self: flex-start;

  color: white;

  font-size: 20px;
  font-weight: 700;
`;

export const PopularSongList = styled.section`
  width: 100%;

  display: flex;
  flex-direction: column;
  align-items: flex-start;

  gap: 12px;
`;
