import { Outlet } from 'react-router-dom';
import styled from 'styled-components';
import Header from './Header';

const Layout = () => {
  return (
    <>
      <Header />
      <LayoutContainer>
        <Outlet />
      </LayoutContainer>
    </>
  );
};

export default Layout;

const LayoutContainer = styled.main`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 100%;
  padding: 60px 16.66%;
  background-color: pink;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    padding: 44px 16.66%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    padding: 36px 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 36px 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding: 28px 16px;
  }
`;
