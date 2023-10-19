import { useNavigate } from 'react-router-dom';
import { Flex } from 'shook-layout';
import { styled } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import MyPartList from '@/features/member/components/MyPartList';
import getRandomIntroduction from '@/features/member/utils/getRandomIntroduction';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import { GA_ACTIONS, GA_CATEGORIES } from '@/shared/constants/GAEventName';
import ROUTE_PATH from '@/shared/constants/path';
import sendGAEvent from '@/shared/googleAnalytics/sendGAEvent';

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

      <ProfileFlex $justify="space-between">
        <Box>
          <Title>{user?.nickname ?? 'shook'}</Title>
          <Spacing direction="vertical" size={6} />
          <Introduction>{getRandomIntroduction()}</Introduction>
        </Box>
        <Flex $direction="column">
          <Spacing direction="vertical" size={10} />
          <Avatar src={shookshook} alt="" />
        </Flex>
      </ProfileFlex>

      <Spacing direction="vertical" size={16} />

      <Flex $justify="space-between">
        <Button onClick={goEditPage}>프로필 편집</Button>
        <Button onClick={logoutRedirect}>로그아웃</Button>
      </Flex>

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

const ProfileFlex = styled(Flex)`
  height: 108px;
`;

const Title = styled.h2`
  align-self: flex-start;
  font-size: 20px;
  font-weight: 700;
  color: white;
`;

const Introduction = styled(Box)`
  overflow: hidden;
  display: -webkit-box;

  text-overflow: ellipsis;
  word-break: break-word;

  -webkit-box-orient: vertical;
  -webkit-line-clamp: 3;
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
