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
  text-align: center;
  height: 30px;
  background-color: ${({ theme: { color }, $isActive }) => ($isActive ? 'pink' : color.disabled)};
  color: ${({ theme: { color }, $isActive }) => ($isActive ? color.black : color.white)};
  font-size: 13px;
  padding: 0 8px;
  line-height: 2.3;
  border-radius: 40px;
  max-width: 50px;

  display: flex;
  justify-content: center;
  align-items: center;

  transition: background-color 0.2s ease-in;
`;
