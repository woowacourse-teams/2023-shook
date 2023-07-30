import { useState } from 'react';
import { useParams } from 'react-router-dom';
import { styled } from 'styled-components';
import dummyJacket from '@/assets/image/album-jacket.png';
import { Flex, Spacing } from '@/components/@common';
import ToggleGroup from '@/components/@common/ToggleGroup/ToggleGroup';
import ToggleSwitch from '@/components/@common/ToggleSwitch/ToggleSwitch';
import { KillingPartInfo } from '@/components/KillingPartInfo';
import Youtube from '@/components/Youtube/Youtube';
import { useGetSongDetail } from '@/hooks/song';

const SongPage = () => {
  const { id } = useParams();
  const [player, setPlayer] = useState<YT.Player | undefined>();
  const [isRepeat, setIsRepeat] = useState(true);
  const [killingRank, setKillingRank] = useState('1');
  const { songDetail } = useGetSongDetail(Number(id));

  if (!songDetail) return;
  const { killingParts, singer, title, videoLength, videoUrl } = songDetail;

  // TODO: videoId 구하는 util함수 분리
  const videoId = videoUrl.replace('https://youtu.be/', '');

  const setPlayerAfterReady = ({ target }: YT.PlayerEvent) => {
    setPlayer(target);
  };

  // TODO: 재생관련 로직
  const onChangeValue = (value: string) => {
    setKillingRank(value);
  };

  const toggleRepetition = () => {
    setIsRepeat(!isRepeat);
  };

  return (
    <Container>
      <SongInfoContainer>
        <Jacket src={dummyJacket} alt={`${title} 앨범 자켓`} />
        <Info>
          <SongTitle>{title}</SongTitle>
          <Singer>{singer}</Singer>
        </Info>
      </SongInfoContainer>
      <Spacing direction="vertical" size={20} />
      <Youtube videoId={videoId} start={0} onReady={setPlayerAfterReady} />
      <Spacing direction="vertical" size={20} />
      <SubTitle>
        <PrimarySpan>킬링파트</PrimarySpan> 듣기
      </SubTitle>
      <Spacing direction="vertical" size={10} />
      <ToggleWrapper>
        <ToggleGroup onChangeValue={onChangeValue}>
          <ToggleGroup.button value="1">{'1st'}</ToggleGroup.button>
          <ToggleGroup.button value="2">{'2nd'}</ToggleGroup.button>
          <ToggleGroup.button value="3">{'3rd'}</ToggleGroup.button>
          <ToggleGroup.button value="4">{'전체'}</ToggleGroup.button>
        </ToggleGroup>
      </ToggleWrapper>
      <Spacing direction="vertical" size={10} />
      <SwitchWrapper>
        <SwitchLabel>반복재생</SwitchLabel>
        <ToggleSwitch on={toggleRepetition} off={toggleRepetition} defaultToggle={isRepeat} />
      </SwitchWrapper>
      <Spacing direction="vertical" size={10} />
      <KillingPartInfo killingPart={killingParts[Number(killingRank) - 1]} />
    </Container>
  );
};

export default SongPage;

const Container = styled(Flex)`
  flex-direction: column;
`;
export const SongInfoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;

export const Jacket = styled.img`
  width: 60px;
  height: 60px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 50px;
    height: 50px;
  }
`;

export const Info = styled.div``;

export const SongTitle = styled.p`
  font-size: 24px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

export const Singer = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;
export const SubTitle = styled.h2`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};
`;

export const PrimarySpan = styled.span`
  color: ${({ theme: { color } }) => color.primary};
`;

export const ToggleWrapper = styled.div`
  padding: 8px;
`;
export const SwitchWrapper = styled.div`
  display: flex;
  justify-content: end;
  margin: 0 8px;
  column-gap: 8px;
`;

export const SwitchLabel = styled.label`
  color: ${({ theme: { color } }) => color.white};
  font-size: 14px;
`;
