import { useEffect } from 'react';
import styled from 'styled-components';
import PartItem from './PartItem';
import type { LikeKillingPart } from './MyPartList';

interface PartListProps {
  parts: LikeKillingPart[];
}

const PART_LIST_SCROLL_TOP = 180;

const PartList = ({ parts }: PartListProps) => {
  useEffect(() => {
    if (window.scrollY > PART_LIST_SCROLL_TOP) {
      window.scrollTo({ top: PART_LIST_SCROLL_TOP, behavior: 'smooth' });
    }
  }, [parts]);

  return (
    <PartListContainer>
      {parts.map((part, i) => (
        <PartItem key={part.partId} rank={i + 1} {...part} />
      ))}
    </PartListContainer>
  );
};

export default PartList;

const PartListContainer = styled.ol`
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: flex-start;

  width: 100%;
`;
