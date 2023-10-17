import { useEffect } from 'react';
import styled from 'styled-components';
import PartItem from './PartItem';
import type { LikeKillingPart } from './MyPartList';

interface PartListProps {
  parts: LikeKillingPart[];
}

const PART_LIST_SCROLL_TOP = 148;

const PartList = ({ parts }: PartListProps) => {
  useEffect(() => {
    if (window.scrollY > PART_LIST_SCROLL_TOP) {
      window.scrollTo({ top: PART_LIST_SCROLL_TOP, behavior: 'smooth' });
    }
  }, [parts]);

  return (
    <PopularSongList>
      {parts.map((part, i) => {
        return (
          <Li key={part.partId}>
            <PartItem {...part} rank={i + 1} />
          </Li>
        );
      })}
    </PopularSongList>
  );
};

export default PartList;

const PopularSongList = styled.ol`
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: flex-start;

  width: 100%;
`;

const Li = styled.li`
  width: 100%;
  padding: 0 10px;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;
