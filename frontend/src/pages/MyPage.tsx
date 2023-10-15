import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import MyPartList from '@/features/member/components/MyPartList';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import { GA_ACTIONS, GA_CATEGORIES } from '@/shared/constants/GAEventName';
import ROUTE_PATH from '@/shared/constants/path';
import sendGAEvent from '@/shared/googleAnalytics/sendGAEvent';

const introductions = [
  '아무 노래나 일단 틀어',
  '또 물보라를 일으켜',
  '난 내가 말야, 스무살쯤엔 요절할 천재일줄만 알고',
  'You make me feel special',
  '우린 참 별나고 이상한 사이야',
];

const MyPage = () => {
  const { user, logout } = useAuthContext();
  const navigate = useNavigate();

  const logoutRedirect = () => {
    sendGAEvent({
      action: GA_ACTIONS.LOGOUT,
      category: GA_CATEGORIES.MY_PAGE,
      memberId: user?.memberId,
    });
    logout();
    navigate(ROUTE_PATH.ROOT);
  };

  const goEditPage = () => {
    sendGAEvent({
      action: GA_ACTIONS.EDIT_PROFILE,
      category: GA_CATEGORIES.MY_PAGE,
      memberId: user?.memberId,
    });

    navigate(`/${ROUTE_PATH.EDIT_PROFILE}`);
  };

  return (
    <Container>
      <SRHeading>마이 페이지</SRHeading>

      <SpaceBetween>
        <Box>
          <Title>{user?.nickname}</Title>
          <Spacing direction="vertical" size={6} />
          <Box>{introductions[Math.floor(Math.random() * introductions.length)]}</Box>
        </Box>
        <Avatar src={shookshook} alt="" />
      </SpaceBetween>

      <Spacing direction="vertical" size={24} />

      <SpaceBetween>
        <Button onClick={goEditPage}>프로필 편집</Button>
        <Button onClick={logoutRedirect}>로그아웃</Button>
      </SpaceBetween>

      <Spacing direction="vertical" size={12} />

      <MyPartList />

      <Spacing direction="vertical" size={24} />
    </Container>
  );
};

export default MyPage;

const Container = styled.section`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const Box = styled.div`
  flex: 1;
  width: 100%;
`;

const Title = styled.h2`
  align-self: flex-start;
  font-size: 20px;
  font-weight: 700;
  color: white;
`;

const SpaceBetween = styled(Flex)`
  justify-content: space-between;
`;

const Avatar = styled.img`
  width: 60px;
  height: 60px;
  border-radius: 50%;
`;

const Button = styled.button`
  width: 48%;
  height: 40px;

  font-weight: 700;

  border: 1.6px solid ${({ theme }) => theme.color.secondary};
  border-radius: 12px;
`;
