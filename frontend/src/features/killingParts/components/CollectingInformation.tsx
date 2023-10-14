import { styled } from 'styled-components';

const CollectingInformation = () => {
  return (
    <Container>
      <RegisterTitle>나만의 파트 저장하기</RegisterTitle>
      <Warning>같은 파트에 대한 중복 등록은 한 번의 등록으로 처리됩니다.</Warning>
    </Container>
  );
};

export default CollectingInformation;

const Container = styled.div``;

const RegisterTitle = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 22px;
  }
`;

const Warning = styled.div`
  font-size: 14px;
  color: ${({ theme: { color } }) => color.subText};

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;
