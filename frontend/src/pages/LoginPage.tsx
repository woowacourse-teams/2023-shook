import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import googleLogo from '@/assets/icon/google-logo.svg';
import kakaoLogo from '@/assets/icon/kakao-logo.svg';
import slogan from '@/assets/image/shook-slogan.jpg';
import { googleAuthUrl, kakaoAuthUrl } from '@/features/auth/constants/authUrls';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';

const LoginPage = () => {
  const goToGoogleAuth = () => {
    if (window.navigator.userAgent.match(/kakaotalk/i)) {
      alert('카카오톡에서 구글 로그인은 불가능합니다.');
      return;
    }
    window.location.href = googleAuthUrl;
  };

  return (
    <LayoutContainer>
      <div>
        <Spacing direction="vertical" size={120} />
        <Link to={ROUTE_PATH.ROOT} aria-label="shook 홈으로 가기">
          <MainLogo src={slogan} alt="logo" aria-hidden="true" />
        </Link>
      </div>
      <LoginButtonContainer>
        <GoogleLoginButton onClick={goToGoogleAuth}>
          <GoogleLogin>
            <Spacing direction="horizontal" size={10} />
            <LoginLogo src={googleLogo} alt="google logo" />
            <LoginText>구글로 로그인하기</LoginText>
            <Spacing direction="horizontal" size={10} />
          </GoogleLogin>
        </GoogleLoginButton>
        <LoginLink href={kakaoAuthUrl}>
          <KakaoLogin>
            <Spacing direction="horizontal" size={10} />
            <LoginLogo src={kakaoLogo} alt="google logo" />
            <LoginText>카카오로 로그인하기</LoginText>
            <Spacing direction="horizontal" size={10} />
          </KakaoLogin>
        </LoginLink>
      </LoginButtonContainer>
      <div>
        <UnderLineAnchor
          href="https://silversound.notion.site/acc5cba213f041519ebbfe6c1960ef58?pvs=4"
          target="__blank"
        >
          개인정보 처리방침
        </UnderLineAnchor>
        <Spacing direction="vertical" size={80} />
      </div>
    </LayoutContainer>
  );
};

const LayoutContainer = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;

  width: 100%;
  min-height: 100vh;
  padding: 0 16.66%;

  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.black};

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    padding: 0 8.3%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    padding: 0 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    padding: 0 4.16%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    padding: 0 16px;
  }

  @supports (-webkit-appearance: none) and (stroke-color: transparent) {
    min-height: -webkit-fill-available;
  }
`;

const LoginButtonContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
  align-items: center;
  justify-content: center;

  width: 100%;
`;

const MainLogo = styled.img`
  aspect-ratio: 3 / 1;
  width: 500px;
`;

const PlatformName = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;

  width: 400px;
  height: 60px;

  text-align: center;

  border-radius: 12px;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    width: 100%;
  }
`;

const KakaoLogin = styled(PlatformName)`
  background-color: ${({ theme: { color } }) => color.oauth.kakao};
`;

const GoogleLogin = styled(PlatformName)`
  background-color: ${({ theme: { color } }) => color.oauth.google};
`;

const GoogleLoginButton = styled.button`
  flex: 1;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    width: 100%;
  }
`;

const LoginLink = styled.a`
  flex: 1;

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    width: 100%;
  }
`;

const LoginLogo = styled.img`
  width: 30px;
  height: 30px;
`;

const LoginText = styled.div`
  flex: 1;
  font-size: 15pt;
  color: rgba(0, 0, 0, 85%);
`;

const UnderLineAnchor = styled.a`
  text-decoration: underline;
`;

export default LoginPage;
