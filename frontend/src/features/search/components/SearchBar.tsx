import { Flex } from 'shook-layout';
import styled, { css } from 'styled-components';
import cancelIcon from '@/assets/icon/cancel.svg';
import backwardIcon from '@/assets/icon/left-arrow.svg';
import searchIcon from '@/assets/icon/search.svg';
import useSearchBar from '../hooks/useSearchBar';
import SearchPreviewSheet from './SearchPreviewSheet';

const SearchBar = () => {
  const {
    isSearching,
    searchQuery,
    inputRef,
    singerSearchPreview,
    startSearch,
    endSearchOnBlur,
    endSearch,
    changeQuery,
    resetQuery,
    search,
  } = useSearchBar();

  const isQueryFilled = searchQuery.length !== 0;

  return (
    <FlexSearchBox
      as="form"
      $align="center"
      $justify="space-between"
      onSubmit={search}
      $isSearching={isSearching}
    >
      <BackwardButton type="button" onClick={endSearch} $isSearching={isSearching} />
      <SearchInput
        type="text"
        placeholder="아티스트 검색"
        ref={inputRef}
        value={searchQuery}
        onChange={changeQuery}
        onFocus={startSearch}
        onBlur={endSearchOnBlur}
        $isSearching={isSearching}
      />
      {isQueryFilled && (
        <ResetQueryButton
          id="query-reset-button"
          type="button"
          onClick={resetQuery}
          $isSearching={isSearching}
        />
      )}
      <SearchButton id="search-button" type="submit" $isSearching={isSearching} />
      <SearchBarExpandButton type="button" onClick={startSearch} $isSearching={isSearching} />
      {isSearching && (
        <SearchPreviewSheet result={singerSearchPreview ?? []} endSearch={endSearch} />
      )}
    </FlexSearchBox>
  );
};

export default SearchBar;

const searchButtonStyles = css`
  width: 20px;
  height: 20px;
  background: url(${searchIcon}) transparent no-repeat;
  background-size: contain;
`;

const FlexSearchBox = styled(Flex)<{ $isSearching: boolean }>`
  position: relative;

  height: 34px;
  padding: 0 10px;

  background-color: ${({ theme }) => theme.color.black200};
  border-radius: 14px;

  transition: flex 0.2s ease;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    flex: ${({ $isSearching }) => $isSearching && 1};
  }
`;

const BackwardButton = styled.button<{ $isSearching: boolean }>`
  position: absolute;
  top: 50%;
  left: 10px;
  transform: translate(0, -50%);

  display: none;

  width: 20px;
  height: 20px;

  background: url(${backwardIcon}) transparent no-repeat;
  background-size: contain;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    display: ${({ $isSearching }) => $isSearching && 'block'};
  }
`;

const SearchInput = styled.input<{ $isSearching: boolean }>`
  width: 220px;
  padding: 0 40px 0 8px;

  color: white;

  background-color: transparent;
  border: none;
  outline: none;

  transition: width 0.3s ease;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: ${({ $isSearching }) => !$isSearching && 0};
    padding: ${({ $isSearching }) => ($isSearching ? '0 40px 0 28px' : 0)};
    visibility: ${({ $isSearching }) => !$isSearching && 'hidden'};
  }
`;

const ResetQueryButton = styled.button<{ $isSearching: boolean }>`
  position: absolute;
  top: 50%;
  right: 40px;
  transform: translate(0, -50%);

  width: 26px;
  height: 26px;

  background: url(${cancelIcon}) transparent no-repeat;
  background-size: contain;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    display: ${({ $isSearching }) => !$isSearching && 'none'};
  }
`;

const SearchButton = styled.button<{ $isSearching: boolean }>`
  ${searchButtonStyles}

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    display: ${({ $isSearching }) => !$isSearching && 'none'};
  }
`;

const SearchBarExpandButton = styled.button<{ $isSearching: boolean }>`
  ${searchButtonStyles}
  display: none;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    display: ${({ $isSearching }) => !$isSearching && 'block'};
  }
`;
