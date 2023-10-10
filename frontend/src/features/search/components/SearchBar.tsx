import styled, { css } from 'styled-components';
import cancelIcon from '@/assets/icon/cancel.svg';
import backwardIcon from '@/assets/icon/left-arrow.svg';
import searchIcon from '@/assets/icon/search.svg';
import Flex from '@/shared/components/Flex/Flex';
import useSearchBar from '../hooks/useSearchBar';
import ResultSheet from './ResultSheet';

const SearchBar = () => {
  const {
    isSearching,
    searchQuery,
    inputRef,
    artistSearchPreview,
    startSearch,
    endSearchOnBlur,
    endSearchOnClick,
    changeQuery,
    resetQuery,
  } = useSearchBar();

  const isQueryFilled = searchQuery.length !== 0;

  return (
    <SearchBox as="form" $align="center" $justify="space-between" $isSearching={isSearching}>
      <BackwardButton type="button" onClick={endSearchOnClick} $isSearching={isSearching} />
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
      <SearchButton type="submit" $isSearching={isSearching} />
      <SearchBarExpendButton type="button" onClick={startSearch} $isSearching={isSearching} />
      {isSearching && <ResultSheet result={artistSearchPreview ?? []} />}
    </SearchBox>
  );
};

export default SearchBar;

const searchButtonStyles = css`
  width: 20px;
  height: 20px;
  background: url(${searchIcon}) transparent no-repeat;
  background-size: contain;
`;

const SearchBox = styled(Flex)<{ $isSearching: boolean }>`
  position: relative;

  height: 34px;
  padding: 0 10px;

  background-color: ${({ theme }) => theme.color.black200};
  border-radius: 16px;

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
    width: ${({ $isSearching }) => !$isSearching && '0px'};
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

const SearchBarExpendButton = styled.button<{ $isSearching: boolean }>`
  ${searchButtonStyles}
  display: none;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    display: ${({ $isSearching }) => !$isSearching && 'block'};
  }
`;
