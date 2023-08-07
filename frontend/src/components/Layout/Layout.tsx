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
  flex-direction: column;
  align-items: center;
  width: 100%;
  padding: 60px 16.66%;
  min-height: calc(100vh - ${({ theme }) => theme.headerHeight.desktop});
  background-color: ${({ theme: { color } }) => color.black};
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    padding: 36px 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 36px 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    min-height: calc(100vh - ${({ theme }) => theme.headerHeight.mobile});
    padding: 16px 16px;
  }
`;
