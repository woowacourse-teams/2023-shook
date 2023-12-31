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
  padding: 0 12.33%;

  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.black};

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    padding: 0 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 0 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding: 0 16px;
  }

  @supports (-webkit-appearance: none) and (stroke-color: transparent) {
    min-height: -webkit-fill-available;
  }
`;
