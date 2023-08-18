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
  min-height: 100vh;
  padding: ${({ theme: { mainTopBottomPadding } }) => `${mainTopBottomPadding.desktop} 16.66%`};

  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.black};

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    padding: ${({ theme: { mainTopBottomPadding } }) => `${mainTopBottomPadding.desktop} 8.33%`};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: ${({ theme: { mainTopBottomPadding } }) => `${mainTopBottomPadding.tablet} 4.16%`};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding: ${({ theme: { mainTopBottomPadding } }) => `${mainTopBottomPadding.tablet} 4.16%`};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding: ${({ theme: { mainTopBottomPadding } }) => `${mainTopBottomPadding.xxs} 16px`};
  }
`;
