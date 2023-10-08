import { useRef, useState } from 'react';
import styled, { css } from 'styled-components';
import cancelIcon from '@/assets/icon/cancel.svg';
import searchIcon from '@/assets/icon/search.svg';
import Flex from '@/shared/components/Flex/Flex';
import ResultSheet from './ResultSheet';

const SearchBar = () => {
  const [isSearching, setIsSearching] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const inputRef = useRef<HTMLInputElement | null>(null);

  const startSearch: React.MouseEventHandler & React.FocusEventHandler = () => {
    setIsSearching(true);
  };

  const endSearch: React.FocusEventHandler = (e) => {
    if (e.relatedTarget?.id === 'query-reset-button') return;

    setIsSearching(false);
  };

  const changeQuery: React.ChangeEventHandler<HTMLInputElement> = ({ currentTarget }) => {
    setSearchQuery(currentTarget.value);
  };

  const resetQuery: React.MouseEventHandler = () => {
    setSearchQuery('');
    inputRef.current?.focus();
  };

  const isQueryFilled = searchQuery.length !== 0;

  return (
    <SearchBox $align="center" $isSearching={isSearching}>
      <SearchInput
        type="text"
        placeholder="아티스트 검색"
        ref={inputRef}
        value={searchQuery}
        onChange={changeQuery}
        onFocus={startSearch}
        $isSearching={isSearching}
      />
      <SearchButton $isSearching={isSearching} />
      <SearchBarExpendButton type="button" onClick={startSearch} $isSearching={isSearching} />
      {isQueryFilled && (
        <ResetQueryButton id="query-reset-button" type="button" onClick={resetQuery} />
      )}
      {isSearching && <ResultSheet />}
    </SearchBox>
  );
};

export default SearchBar;

const inputCloseStyles = css`
  width: 0px;
  padding: 0;
`;

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

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    flex: ${({ $isSearching }) => $isSearching && 1};
  }
`;

const SearchButton = styled.button<{ $isSearching: boolean }>`
  ${searchButtonStyles}
  position: absolute;
  top: 50%;
  right: 10px;
  transform: translate(0, -50%);

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

const ResetQueryButton = styled.button`
  position: absolute;
  top: 50%;
  right: 40px;
  transform: translate(0, -50%);

  width: 26px;
  height: 26px;

  background: url(${cancelIcon}) transparent no-repeat;
  background-size: contain;
`;

const SearchInput = styled.input<{ $isSearching: boolean }>`
  max-width: 100%;
  padding: 0 60px 0 10px;

  color: white;

  background-color: ${({ theme }) => theme.color.black200};
  border: none;
  outline: none;

  transition: all 0.2s ease;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    ${({ $isSearching }) => !$isSearching && inputCloseStyles}
  }
`;
