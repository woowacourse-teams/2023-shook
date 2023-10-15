import { useNavigate } from 'react-router-dom';
import { styled } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import PartItem from '@/features/member/components/PartItem';
import Flex from '@/shared/components/Flex';
import Spacing from '@/shared/components/Spacing';
import SRHeading from '@/shared/components/SRHeading';
import { GA_ACTIONS, GA_CATEGORIES } from '@/shared/constants/GAEventName';
import ROUTE_PATH from '@/shared/constants/path';
import sendGAEvent from '@/shared/googleAnalytics/sendGAEvent';
import useFetch from '@/shared/hooks/useFetch';
import fetcher from '@/shared/remotes';
import type { KillingPart, SongDetail } from '@/shared/types/song';

const introductions = [
  '아무 노래나 일단 틀어',
  '또 물보라를 일으켜',
  '난 내가 말야, 스무살쯤엔 요절할 천재일줄만 알고',
  'You make me feel special',
  '우린 참 별나고 이상한 사이야',
];

type LikeKillingPart = Pick<SongDetail, 'title' | 'singer' | 'albumCoverUrl'> &
  Pick<KillingPart, 'start' | 'end'> & {
    songId: number;
    partId: number;
  };

const MyPage = () => {
  const { user, logout } = useAuthContext();
  const { data: likes } = useFetch<LikeKillingPart[]>(() => fetcher('/my-page', 'get'));
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

  if (!likes) return null;

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

      <Spacing direction="vertical" size={24} />

      <Subtitle>좋아요한 킬링파트 {likes.length.toLocaleString('ko-KR')}개</Subtitle>

      <Spacing direction="vertical" size={24} />

      <PopularSongList>
        {likes.map(({ songId, title, singer, albumCoverUrl, partId, start, end }, i) => {
          return (
            <Li key={partId}>
              <PartItem
                songId={songId}
                partId={partId}
                rank={i + 1}
                albumCoverUrl={albumCoverUrl}
                title={title}
                singer={singer}
                start={start}
                end={end}
              />
            </Li>
          );
        })}
      </PopularSongList>
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

const Li = styled.li`
  width: 100%;
  padding: 0 10px;

  &:hover,
  &:focus {
    background-color: ${({ theme }) => theme.color.secondary};
  }
`;

const Title = styled.h2`
  align-self: flex-start;
  font-size: 20px;
  font-weight: 700;
  color: white;
`;

const PopularSongList = styled.ol`
  display: flex;
  flex-direction: column;
  gap: 12px;
  align-items: flex-start;

  width: 100%;
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

const Subtitle = styled.div`
  width: 100%;
  height: 36px;
  font-size: 18px;
  border-bottom: 1px solid ${({ theme }) => theme.color.white};
`;
