import { useCallback, useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ROUTE_PATH from '@/shared/constants/path';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';
import useFetch from '@/shared/hooks/useFetch';
import { getSingerSearchPreview } from '../remotes/search';

const useSearchBar = () => {
  const [isSearching, setIsSearching] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const inputRef = useRef<HTMLInputElement | null>(null);
  const navigate = useNavigate();

  const { data: singerSearchPreview, fetchData: fetchSingerSearchPreview } = useFetch(
    () => getSingerSearchPreview(searchQuery),
    false
  );

  useDebounceEffect(fetchSingerSearchPreview, searchQuery, 300);

  const search: React.FormEventHandler = useCallback(
    (e) => {
      e.preventDefault();
      setIsSearching(false);
      navigate(`${ROUTE_PATH.SEARCH_RESULT}?name=${searchQuery}`);
    },
    [searchQuery, navigate]
  );

  const startSearch: React.MouseEventHandler & React.FocusEventHandler = useCallback(() => {
    setIsSearching(true);
  }, []);

  const endSearchOnBlur: React.FocusEventHandler<HTMLInputElement> = useCallback(
    ({ relatedTarget }) => {
      if (relatedTarget?.id === 'query-reset-button') return;

      setIsSearching(false);
    },
    []
  );

  const endSearchOnClick: React.MouseEventHandler<HTMLButtonElement> = useCallback(() => {
    setIsSearching(false);
  }, []);

  const changeQuery: React.ChangeEventHandler<HTMLInputElement> = useCallback(
    ({ currentTarget }) => {
      setSearchQuery(currentTarget.value);
    },
    []
  );

  const resetQuery: React.MouseEventHandler = useCallback(() => {
    setSearchQuery('');
    inputRef.current?.focus();
  }, []);

  useEffect(() => {
    if (isSearching) {
      inputRef.current?.focus();
    } else {
      inputRef.current?.blur();
    }
  }, [isSearching]);

  return {
    isSearching,
    searchQuery,
    inputRef,
    singerSearchPreview,
    startSearch,
    endSearchOnBlur,
    endSearchOnClick,
    changeQuery,
    resetQuery,
    search,
  };
};

export default useSearchBar;
