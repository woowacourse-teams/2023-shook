import { styled } from 'styled-components';

export const Grid = styled.p`
  display: grid;
  column-gap: 12px;
  padding: 6px 0;
  grid-template:
    'rank thumbnail title' 30px
    'rank thumbnail singer' 30px
    'rank thumbnail info' 20px
    / 20px 80px 200px;

  color: ${({ theme: { color } }) => color.white};
`;

export const Rank = styled.div`
  grid-area: rank;

  display: flex;
  justify-content: center;
  align-items: center;

  font-weight: 800;
`;

export const SongTitle = styled.div`
  grid-area: title;

  font-size: 16px;
  font-weight: 800;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const Singer = styled.div`
  grid-area: singer;

  font-size: 12px;

  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
`;

export const Info = styled.div`
  grid-area: info;

  color: #808191;
  font-size: 12px;
`;
