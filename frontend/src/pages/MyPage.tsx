import { useParams } from 'react-router-dom';

const MyPage = () => {
  const { id: memberId } = useParams();
  return <>MyPage: {memberId}</>;
};

export default MyPage;
