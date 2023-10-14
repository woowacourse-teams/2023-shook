import styled from 'styled-components';
import type { PropsWithChildren } from 'react';

interface PlayerBadge extends PropsWithChildren {
  isActive?: boolean;
}

const PlayerBadge = ({ isActive = false, children }: PlayerBadge) => {
  return <Badge $isActive={isActive}>{children}</Badge>;
};

export default PlayerBadge;

const Badge = styled.span<{ $isActive: boolean }>`
  display: flex;
  align-items: center;

  height: 30px;
  padding: 0 10px;

  font-size: 13px;
  color: ${({ theme: { color }, $isActive }) => ($isActive ? color.black : color.white)};
  text-align: center;

  background-color: ${({ theme: { color }, $isActive }) => ($isActive ? 'pink' : color.disabled)};
  border-radius: 40px;

  transition: background-color 0.2s ease-in;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 14px;
  }
`;
