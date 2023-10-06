import { useRef, useState } from 'react';
import styled from 'styled-components';
import cancelIcon from '@/assets/icon/cancel.svg';
import searchIcon from '@/assets/icon/search.svg';
import Flex from '@/shared/components/Flex/Flex';
import ResultSheet from './ResultSheet';

const SearchBar = () => {
  const [search, setSearch] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const inputRef = useRef<HTMLInputElement | null>(null);

  const openSearchBar: React.MouseEventHandler = () => {
    setSearch(true);
  };

  const closeSearchBar: React.FocusEventHandler = (e) => {
    if (e.relatedTarget?.id === 'query-reset-button') return;

    setSearch(false);
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
    <SearchBox $align="center">
      {search ? (
        <>
          <SearchInput
            type="text"
            placeholder="아티스트 검색"
            autoFocus
            ref={inputRef}
            value={searchQuery}
            onChange={changeQuery}
            onBlur={closeSearchBar}
          />
          {isQueryFilled && (
            <ResetQueryButton id="query-reset-button" type="button" onClick={resetQuery} />
          )}
          <ResultSheet />
        </>
      ) : (
        <SearchButton type="button" onClick={openSearchBar} />
      )}
    </SearchBox>
  );
};

export default SearchBar;

const SearchBox = styled(Flex)`
  position: relative;

  height: 30px;
  padding: 0 10px;

  background-color: ${({ theme }) => theme.color.black200};
  border-radius: 16px;
`;

const SearchButton = styled.button`
  width: 26px;
  height: 26px;
  background: url(${searchIcon}) transparent no-repeat;
  background-size: contain;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 20px;
    height: 20px;
  }
`;

const ResetQueryButton = styled.button`
  position: absolute;
  top: 50%;
  right: 5px;
  transform: translate(0, -50%);

  width: 26px;
  height: 26px;

  background: url(${cancelIcon}) transparent no-repeat;
  background-size: contain;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 20px;
    height: 20px;
  }
`;

const SearchInput = styled.input`
  padding: 0 24px 0 10px;

  color: white;

  background-color: ${({ theme }) => theme.color.black200};
  border: none;
  outline: none;
`;
