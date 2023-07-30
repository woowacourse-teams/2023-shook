import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyJacket from '@/assets/image/album-jacket.png';
import { Spacing } from '@/components/@common';
import ToggleGroup from '@/components/@common/ToggleGroup/ToggleGroup';
import ToggleSwitch from '@/components/@common/ToggleSwitch/ToggleSwitch';
import { KillingPartInfo } from '@/components/KillingPartInfo';
import Youtube from '@/components/Youtube/Youtube';
import { useGetSongDetail } from '@/hooks/song';
import {
  Info,
  Jacket,
  Wrapper,
  Singer,
  PrimarySpan,
  SongInfoContainer,
  SongTitle,
  SubTitle,
  SwitchLabel,
  SwitchWrapper,
  ToggleWrapper,
} from './SongPage.style';

const SongPage = () => {
  const { id } = useParams();
  const [player, setPlayer] = useState<YT.Player | undefined>();
  const [isRepeat, setIsRepeat] = useState(true);
  const [killingRank, setKillingRank] = useState('');
  const { songDetail } = useGetSongDetail(Number(id));
  const timer = useRef<number>(-1);

  useEffect(() => {
    if (!songDetail) return;
    const part = songDetail.killingParts?.[Number(killingRank) - 1];

    if (!part) {
      player?.seekTo(0, true);
      player?.playVideo();
      return;
    } else {
      player?.seekTo(part.start, true);
    }

    player?.playVideo();

    if (isRepeat) {
      timer.current = window.setInterval(
        () => {
          player?.seekTo(part.start, true);
        },
        (part.end - part.start) * 1000
      );
    }

    return () => {
      window.clearInterval(timer.current);
    };
  }, [isRepeat, killingRank, player, songDetail]);

  if (!songDetail) return;
  const { killingParts, singer, title, videoUrl } = songDetail;

  const videoId = videoUrl.replace('https://youtu.be/', '');

  const setPlayerAfterReady = ({ target }: YT.PlayerEvent) => {
    setPlayer(target);
  };

  const onChangeValue = (value: string) => {
    setKillingRank(value);
  };

  const toggleRepetition = () => {
    setIsRepeat(!isRepeat);
  };

  return (
    <Wrapper>
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
    </Wrapper>
  );
};

export default SongPage;
