import { useEffect, useRef, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import ROUTE_PATH from '@/shared/constants/path';
import useDebounceEffect from '@/shared/hooks/useDebounceEffect';
import useFetch from '@/shared/hooks/useFetch';
import { getArtistSearchPreview } from '../remotes/search';

const useSearchBar = () => {
  const [isSearching, setIsSearching] = useState(false);
  const [searchQuery, setSearchQuery] = useState('');
  const inputRef = useRef<HTMLInputElement | null>(null);
  const navigate = useNavigate();

  const search: React.FormEventHandler = (e) => {
    e.preventDefault();
    navigate(`${ROUTE_PATH.SEARCH_RESULT}?name=${searchQuery}`);
  };

  const { data: artistSearchPreview, fetchData: fetchArtistSearchPreview } = useFetch(
    () => getArtistSearchPreview(searchQuery),
    false
  );

  useDebounceEffect(fetchArtistSearchPreview, searchQuery, 300);

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
    artistSearchPreview,
    startSearch,
    endSearchOnBlur,
    endSearchOnClick,
    changeQuery,
    resetQuery,
    search,
  };
};

export default useSearchBar;
