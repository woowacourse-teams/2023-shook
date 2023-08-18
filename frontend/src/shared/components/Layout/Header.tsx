import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import defaultAvatar from '@/assets/icon/avatar-default.svg';
import logo from '@/assets/icon/shook-logo.svg';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import googleAuthUrl from '@/features/auth/constants/googleAuthUrl';
import ROUTE_PATH from '@/shared/constants/path';
import Avatar from '../Avatar';

const Header = () => {
  const { user } = useAuthContext();

  return (
    <Container>
      <Link to={ROUTE_PATH.ROOT} aria-label="shook 홈으로 가기">
        <Logo src={logo} alt="logo" aria-hidden="true" />
      </Link>
      {user ? (
        <Link to="/my-page">
          <ProfileAvatar src={shookshook} />
        </Link>
      ) : (
        <Link to={googleAuthUrl}>
          <ProfileAvatar src={defaultAvatar} />
        </Link>
      )}
    </Container>
  );
};

export default Header;

const Container = styled.header`
  position: sticky;
  z-index: 1000;
  top: 0px;

  display: flex;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  height: ${({ theme }) => theme.headerHeight.desktop};
  padding: 0 16.66%;

  background-color: ${({ theme: { color } }) => color.black};

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    padding: 0 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 0 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    height: ${({ theme }) => theme.headerHeight.mobile};
    padding: 0 16px;
  }
`;

const Logo = styled.img`
  width: 180px;
  height: 56px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 140px;
    height: 40px;
  }
`;

const ProfileAvatar = styled(Avatar)`
  width: 28px;
  height: 28px;
`;
