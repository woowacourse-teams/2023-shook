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
      {parts.map(({ songId, title, singer, albumCoverUrl, partId, start, end }, i) => {
        return (
          <Li key={partId}>
            <PartItem
              songId={songId}
              partId={partId}
              rank={i + 1}
              albumCoverUrl={albumCoverUrl}
              title={title}
              singer={singer}
              start={start}
              end={end}
            />
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
