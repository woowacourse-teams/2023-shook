import { createPortal } from 'react-dom';
import { Link, useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import Thumbnail from '@/features/songs/components/Thumbnail';
import Flex from '@/shared/components/Flex/Flex';
import ROUTE_PATH from '@/shared/constants/path';
import type { SingerSearchPreview } from '../types/search';

interface ResultSheetProps {
  result: SingerSearchPreview[];
}

const ResultSheet = ({ result }: ResultSheetProps) => {
  const navigate = useNavigate();

  const hasResult = result.length > 0;

  return createPortal(
    <SheetContainer>
      <SheetTitle $align="center" aria-disabled>
        아티스트
      </SheetTitle>
      <PreviewItemList as="ul" $direction="column" $gap={16} aria-label="아티스트 검색 결과">
        {result.map(({ id, singer, profileImageUrl }) => (
          <PreviewItem
            key={id}
            onMouseDown={() => navigate(`${ROUTE_PATH.SINGER_DETAIL}/${id}`)}
            aria-label={`${singer} 상세 페이지 바로가기`}
            as="li"
            $align="center"
            $gap={16}
          >
            <Thumbnail src={profileImageUrl} alt="" size="sm" />
            <Singer aria-disabled>{singer}</Singer>
          </PreviewItem>
        ))}
      </PreviewItemList>
      {!hasResult && <DefaultMessage>검색 결과가 없습니다</DefaultMessage>}
    </SheetContainer>,
    document.body
  );
};

export default ResultSheet;

const SheetContainer = styled.div`
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
  box-shadow: 0px 0px 10px #ffffff49;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    right: 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    right: 0%;
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

const SheetTitle = styled(Flex)`
  position: sticky;
  top: 0;
  left: 0;

  height: 50px;

  font-weight: 700;
  letter-spacing: 2px;

  background-color: #121212;
`;

const PreviewItemList = styled(Flex)``;

const PreviewItem = styled(Flex)`
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
const DefaultMessage = styled.p``;
