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

      if (searchQuery.length === 0) return;

      setIsSearching(false);
      navigate(`${ROUTE_PATH.SEARCH_RESULT}?name=${searchQuery}`);
    },
    [searchQuery, navigate]
  );

  const startSearch = useCallback(() => {
    setIsSearching(true);
  }, []);

  const endSearchOnBlur: React.FocusEventHandler<HTMLInputElement> = useCallback(
    ({ relatedTarget }) => {
      if (relatedTarget?.id === 'search-preview-sheet') return;
      if (relatedTarget?.id === 'query-reset-button') return;
      if (relatedTarget?.id === 'search-button') return;

      setIsSearching(false);
    },
    []
  );

  const endSearch = useCallback(() => {
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

  useEffect(() => {
    if (isSearching) {
      document.body.style.overflow = 'hidden';
    }

    return () => {
      document.body.style.overflow = 'auto';
    };
  }, [isSearching]);

  return {
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
  };
};

export default useSearchBar;
