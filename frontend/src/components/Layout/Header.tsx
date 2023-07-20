import { styled } from 'styled-components';
import logo from '@/assets/shook-logo.svg';

const Header = () => {
  return (
    <Container>
      <Logo src={logo} alt="logo" />
    </Container>
  );
};

export default Header;

const Container = styled.header`
  display: flex;
  align-items: center;

  width: 100%;
  height: 40px;
  background-color: black;

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

const Logo = styled.img``;
