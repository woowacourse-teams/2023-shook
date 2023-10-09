import { useEffect, useRef, useState } from 'react';

const useSearchBar = () => {
  const [isSearching, setIsSearching] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const inputRef = useRef<HTMLInputElement | null>(null);

  const startSearch: React.MouseEventHandler & React.FocusEventHandler = () => {
    setIsSearching(true);
  };

  const endSearchOnBlur: React.FocusEventHandler<HTMLInputElement> = (e) => {
    if (e.relatedTarget?.id === 'query-reset-button') return;

    setIsSearching(false);
  };

  const endSearchOnClick: React.MouseEventHandler<HTMLButtonElement> = () => {
    setIsSearching(false);
  };

  const changeQuery: React.ChangeEventHandler<HTMLInputElement> = ({ currentTarget }) => {
    setSearchQuery(currentTarget.value);
  };

  const resetQuery: React.MouseEventHandler = () => {
    setSearchQuery('');
    inputRef.current?.focus();
  };

  useEffect(() => {
    if (!isSearching) return;
    inputRef.current?.focus();
  }, [isSearching]);

  return {
    isSearching,
    searchQuery,
    inputRef,
    startSearch,
    endSearchOnBlur,
    endSearchOnClick,
    changeQuery,
    resetQuery,
  };
};

export default useSearchBar;
