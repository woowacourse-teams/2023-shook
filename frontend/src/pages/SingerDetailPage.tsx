import useValidParams from '@/shared/hooks/useValidParams';

const SingerDetailPage = () => {
  const { singerId } = useValidParams();

  return <div>{singerId}</div>;
};

export default SingerDetailPage;
