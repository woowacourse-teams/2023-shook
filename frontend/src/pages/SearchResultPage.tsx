import useValidSearchParams from '@/shared/hooks/useValidSearchParams';

const SearchResultPage = () => {
  const { name } = useValidSearchParams('name');

  return <div>{name}</div>;
};

export default SearchResultPage;
