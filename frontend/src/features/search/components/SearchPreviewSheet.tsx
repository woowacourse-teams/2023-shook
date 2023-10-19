import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Thumbnail from '@/features/songs/components/Thumbnail';
import Flex from '@/shared/components/Flex/Flex';
import ROUTE_PATH from '@/shared/constants/path';
import type { SingerSearchPreview } from '../types/search.type';

interface ResultSheetProps {
  result: SingerSearchPreview[];
  endSearch: () => void;
}

const SearchPreviewSheet = ({ result, endSearch }: ResultSheetProps) => {
  const navigate = useNavigate();

  const hasResult = result.length > 0;

  const goToSingerDetailPage = (id: number) => {
    endSearch();
    navigate(`${ROUTE_PATH.SINGER_DETAIL}/${id}`);
  };

  return (
    <>
      <Backdrop onClick={endSearch} />
      <SheetContainer
        id="search-preview-sheet"
        tabIndex={-1}
        aria-label="아티스트 검색 결과 미리보기"
      >
        <FlexSheetTitle $align="center" aria-hidden>
          아티스트
        </FlexSheetTitle>
        <FlexPreviewItemList as="ul" $direction="column" $gap={16}>
          {result.map(({ id, singer, profileImageUrl }) => (
            <FlexPreviewItem key={id} as="li">
              <FlexGoToDetail
                as="button"
                type="button"
                onMouseDown={() => goToSingerDetailPage(id)}
                aria-label={`${singer} 상세 페이지 바로가기`}
                $gap={16}
                $align="center"
              >
                <Thumbnail src={profileImageUrl} alt="" size="sm" />
                <Singer aria-hidden>{singer}</Singer>
              </FlexGoToDetail>
            </FlexPreviewItem>
          ))}
        </FlexPreviewItemList>
        {!hasResult && <DefaultMessage>검색 결과가 없습니다</DefaultMessage>}
      </SheetContainer>
    </>
  );
};

export default SearchPreviewSheet;

export const Backdrop = styled.div`
  position: fixed;
  top: 80px;
  left: 0;

  width: 100%;
  height: 100%;

  background-color: rgba(0, 0, 0, 0.5);
`;

const SheetContainer = styled.section`
  position: fixed;
  z-index: 2000;
  top: 70px;
  right: 12.33%;

  overflow-y: scroll;

  width: 340px;
  height: auto;
  max-height: 320px;
  padding: 0 16px 16px 16px;

  color: white;

  background-color: #121212;
  border-radius: 8px;
  box-shadow: 0 0 10px #ffffff49;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    right: 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    right: 0;
    width: 100%;
    min-height: 100%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    top: 60px;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    top: 50px;
  }
`;

const FlexSheetTitle = styled(Flex)`
  position: sticky;
  top: 0;
  left: 0;

  height: 50px;

  font-size: 18px;
  font-weight: 700;

  background-color: #121212;
`;

const FlexPreviewItemList = styled(Flex)``;

const FlexPreviewItem = styled(Flex)`
  cursor: pointer;

  width: 100%;
  height: 66px;
  padding: 8px;

  background-color: ${({ theme: { color } }) => color.black400};
  border-radius: 4px;

  &:hover {
    background-color: ${({ theme: { color } }) => color.secondary};
  }
`;

const Singer = styled.p`
  font-weight: 700;
  letter-spacing: 1px;
`;
const DefaultMessage = styled.p`
  font-size: 14px;
`;

const FlexGoToDetail = styled(Flex)`
  width: 100%;
`;
