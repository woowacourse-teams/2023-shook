import { useState } from 'react';
import styled from 'styled-components';
import Spacing from '@/shared/components/Spacing';
import useFetch from '@/shared/hooks/useFetch';
import { getLikeParts, getMyParts } from '../remotes/myPage';
import PartList from './PartList';
import type { MyPageTab } from '../types/myPage';
import type { KillingPart, SongDetail } from '@/shared/types/song';

export type LikeKillingPart = Pick<SongDetail, 'title' | 'singer' | 'albumCoverUrl'> &
  Pick<KillingPart, 'start' | 'end'> & {
    songId: number;
    partId: number;
  };

const MyPartList = () => {
  const [tab, setTab] = useState<MyPageTab>('Like');

  const { data: likes } = useFetch<LikeKillingPart[]>(getLikeParts);
  const { data: myParts } = useFetch<LikeKillingPart[]>(getMyParts);

  if (!likes || !myParts) {
    return null;
  }

  const partTabItems: { tab: MyPageTab; title: string; parts: LikeKillingPart[] }[] = [
    { tab: 'Like', title: '좋아요 한 킬링파트', parts: likes },
    { tab: 'MyKillingPart', title: '내 킬링파트', parts: myParts },
  ];

  return (
    <>
      <Tabs role="tablist">
        {partTabItems.map((option) => (
          <TabItem
            key={option.tab}
            $isActive={tab === option.tab}
            onClick={() => setTab(option.tab)}
          >
            {option.title}
          </TabItem>
        ))}
      </Tabs>

      <Spacing direction="vertical" size={24} />

      {partTabItems.map((option) => (
        <PartList key={option.tab} parts={option.parts} isShow={tab === option.tab} />
      ))}
    </>
  );
};

export default MyPartList;

const Tabs = styled.ul`
  position: sticky;
  top: ${({ theme }) => theme.headerHeight.desktop};
  display: flex;
  background-color: ${({ theme: { color } }) => color.black};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    top: ${({ theme }) => theme.headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    top: ${({ theme }) => theme.headerHeight.xxs};
  }
`;

const TabItem = styled.li<{ $isActive?: boolean }>`
  cursor: pointer;

  flex-shrink: 1;

  width: 100%;
  padding: 15px 20px;

  color: ${({ $isActive, theme: { color } }) =>
    $isActive ? color.white : color.disabledBackground};
  text-align: center;

  border-bottom: 2px solid
    ${({ $isActive, theme: { color } }) => ($isActive ? color.white : color.disabled)};

  transition:
    color 0.3s,
    border 0.3s;
`;
