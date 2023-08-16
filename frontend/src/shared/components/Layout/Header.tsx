import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import logo from '@/assets/icon/shook-logo.svg';
import ROUTE_PATH from '@/shared/constants/path';

const Header = () => {
  return (
    <Container>
      <Link to={ROUTE_PATH.ROOT} aria-label="shook 홈으로 가기">
        <Logo src={logo} alt="logo" aria-hidden="true" />
      </Link>
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
