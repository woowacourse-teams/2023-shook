import { useEffect } from 'react';
import styled from 'styled-components';
import PartItem from './PartItem';
import type { LikeKillingPart } from './MyPartList';
import type { MyPageTab } from '../types/myPage';

interface PartListProps {
  parts: LikeKillingPart[];
  isShow: boolean;
  tab: MyPageTab;
}

const PART_LIST_SCROLL_TOP = 180;

const PartList = ({ parts, isShow, tab }: PartListProps) => {
  useEffect(() => {
    if (window.scrollY > PART_LIST_SCROLL_TOP) {
      window.scrollTo({ top: PART_LIST_SCROLL_TOP, behavior: 'smooth' });
    }
  }, [isShow]);

  if (!isShow) {
    return null;
  }

  return (
    <PartListContainer>
      {parts.map((part) => (
        <PartItem key={part.partId} tab={tab} {...part} />
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
