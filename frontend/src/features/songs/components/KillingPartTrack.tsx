import { styled } from 'styled-components';
import emptyHeart from '@/assets/icon/empty-heart.svg';
import playIcon from '@/assets/icon/music-play.svg';

const KillingPartTrack = () => {
  return (
    <Container htmlFor="play">
      <FLexContainer>
        <Rank>1st</Rank>
        <PlayButton id="play" aria-label="1등 킬링파트 재생하기" onClick={() => {}}>
          <ButtonIcon src={playIcon} alt="" />
        </PlayButton>
        <Part>01:10 ~ 01:20</Part>
        <PartLength>10s</PartLength>
      </FLexContainer>
      <FLexContainer>
        <LikeCount>111 Like</LikeCount>
        <LikeButton aria-label="1등 킬링파트에 좋아요 하기" onClick={() => {}}>
          <ButtonIcon src={emptyHeart} />
        </LikeButton>
      </FLexContainer>
    </Container>
  );
};

export default KillingPartTrack;

export const Container = styled.label`
  display: flex;
  padding: 0 12px;
  align-items: center;
  justify-content: space-between;
  gap: 16px;
  width: 100%;
  height: 40px;
  background-color: ${({ theme: { color } }) => color.secondary};
  border-radius: 4px;
  color: ${({ theme: { color } }) => color.white};
  cursor: pointer;
`;

export const Rank = styled.div`
  width: 30px;
  text-align: center;
  font-size: 14px;
  font-style: normal;
  font-weight: 700;
`;

export const PlayButton = styled.button``;

export const Part = styled.span`
  width: 120px;
  text-align: center;
  font-size: 16px;
  font-weight: 700;
`;

export const PartLength = styled.span`
  width: 20px;
  text-align: center;
  font-size: 12px;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    display: none;
  }
`;

export const LikeCount = styled.span`
  width: 50px;
  color: #d8d8d8;
  text-align: right;
  font-size: 12px;
`;

export const LikeButton = styled.button``;

export const ButtonIcon = styled.img``;

export const FLexContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 16px;
`;
