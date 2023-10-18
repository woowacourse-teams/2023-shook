import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { getSingerSearch } from '@/features/search/remotes/search';
import SingerBanner from '@/features/singer/components/SingerBanner';
import SingerSongList from '@/features/singer/components/SingerSongList';
import Flex from '@/shared/components/Flex/Flex';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import useFetch from '@/shared/hooks/useFetch';
import useValidSearchParams from '@/shared/hooks/useValidSearchParams';

const SearchResultPage = () => {
  const { name } = useValidSearchParams('name');
  const navigate = useNavigate();

  const { data: singerDetailList } = useFetch(() => getSingerSearch(name));
  if (!singerDetailList) return;

  const goToSingerDetailPage = (singerId: number) =>
    navigate(`/${ROUTE_PATH.SINGER_DETAIL}/${singerId}`);

  return (
    <Container $direction="column">
      <Title>검색 결과</Title>
      <Spacing direction="vertical" size={48} $sm={{ size: 28 }} />
      {singerDetailList.map(({ id: singerId, profileImageUrl, singer, totalSongCount, songs }) => (
        <React.Fragment key={singerId}>
          <FlexSearchResultContainer $gap={36} $direction="column">
            <SingerBanner
              profileImageUrl={profileImageUrl}
              singer={singer}
              totalSongCount={totalSongCount}
              onClick={() => goToSingerDetailPage(singerId)}
            />
            <SingerSongList songs={songs} />
          </FlexSearchResultContainer>

          <UnderLine />
        </React.Fragment>
      ))}
    </Container>
  );
};

export default SearchResultPage;

const Container = styled(Flex)`
  width: 100%;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const FlexSearchResultContainer = styled(Flex)`
  width: 100%;
`;

const Title = styled.h1`
  font-size: 28px;
  font-weight: 700;
`;

const UnderLine = styled.div`
  width: 100%;
  margin: 32px 0;
  border-bottom: 1px solid ${({ theme: { color } }) => color.black200};
  border-radius: 1px;
`;
