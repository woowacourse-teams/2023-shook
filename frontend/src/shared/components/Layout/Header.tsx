import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import logo from '@/assets/image/logo.png';
import SearchBar from '@/features/artistSearch/components/SearchBar';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import ROUTE_PATH from '@/shared/constants/path';
import Avatar from '../Avatar';
import Flex from '../Flex/Flex';

const Header = () => {
  const { user } = useAuthContext();

  return (
    <Container>
      <Link to={ROUTE_PATH.ROOT} aria-label="shook 홈으로 가기">
        <Logo src={logo} alt="logo" aria-hidden="true" />
      </Link>
      <Flex $align="center" $gap={12}>
        <SearchBar />
        {user ? (
          <Link to={`/${ROUTE_PATH.MY_PAGE}`}>
            <ProfileAvatar src={shookshook} />
          </Link>
        ) : (
          <Link to={ROUTE_PATH.LOGIN}>
            <LoginButton>로그인</LoginButton>
          </Link>
        )}
      </Flex>
    </Container>
  );
};

export default Header;

const Container = styled.header`
  position: fixed;
  z-index: 1000;
  top: 0px;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: ${({ theme }) => theme.headerHeight.desktop};
  padding: 0 12.33%;

  background-color: ${({ theme: { color } }) => color.black};

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    padding: 0 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 0 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    height: ${({ theme }) => theme.headerHeight.mobile};
    padding: 0 16px;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    height: ${({ theme }) => theme.headerHeight.xxs};
  }
`;

const Logo = styled.img`
  aspect-ratio: 177 / 40;
  width: 180px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 140px;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    width: 120px;
  }
`;

const ProfileAvatar = styled(Avatar)`
  width: 28px;
  height: 28px;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    width: 22px;
    height: 22px;
  }
`;

const LoginButton = styled.button`
  padding: 4px 8px;
  color: ${({ theme: { color } }) => color.primary};
  border: 1px solid ${({ theme: { color } }) => color.primary};
  border-radius: 8px;
`;
