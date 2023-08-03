import { useEffect, useRef, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyJacket from '@/assets/image/album-jacket.png';
import { Spacing } from '@/components/@common';
import SRAlert from '@/components/@common/SRAlert';
import SRHeading from '@/components/@common/SRHeading';
import { ToggleGroup } from '@/components/@common/ToggleGroup';
import { ToggleSwitch } from '@/components/@common/ToggleSwitch';
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
  const [killingRank, setKillingRank] = useState<number | null>(null);
  const { songDetail } = useGetSongDetail(Number(id));
  const timer = useRef<number>(-1);

  useEffect(() => {
    if (!songDetail) return;

    const part = songDetail.killingParts?.find((part) => part.rank === killingRank);

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
  const { killingParts, singer, title, songVideoUrl } = songDetail;
  const killingPart = killingParts?.find((part) => part.rank === killingRank);

  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  const setPlayerAfterReady = ({ target }: YT.PlayerEvent) => {
    setPlayer(target);
  };

  const changeKillingRank = (rank: number) => {
    setKillingRank(rank);
  };

  const toggleRepetition = () => {
    setIsRepeat(!isRepeat);
  };

  return (
    <Wrapper>
      <SRHeading>킬링파트 듣기 페이지</SRHeading>
      <SongInfoContainer tabIndex={0} aria-label={`가수 ${singer}의 노래 ${title}`}>
        <Jacket src={dummyJacket} alt={`${title} 앨범`} />
        <Info>
          <SongTitle aria-label={`노래 ${title}`}>{title}</SongTitle>
          <Singer aria-label={`가수 ${singer}`}>{singer}</Singer>
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
        <ToggleGroup onChangeButton={changeKillingRank}>
          <ToggleGroup.Button tabIndex={0} index={1} aria-label="1등 킬링파트 노래 듣기">
            1st
          </ToggleGroup.Button>
          <Spacing direction="horizontal" size={10} />
          <ToggleGroup.Button tabIndex={0} index={2} aria-label="2등 킬링파트 노래 듣기">
            2nd
          </ToggleGroup.Button>
          <Spacing direction="horizontal" size={10} />
          <ToggleGroup.Button index={3} aria-label="3등 킬링파트 노래 듣기">
            3rd
          </ToggleGroup.Button>
          <Spacing direction="horizontal" size={10} />
          <ToggleGroup.Button index={4} aria-label="노래 전체 듣기">
            전체
          </ToggleGroup.Button>
        </ToggleGroup>
      </ToggleWrapper>
      <Spacing direction="vertical" size={10} />
      <SwitchWrapper>
        <SwitchLabel htmlFor="repetition">반복재생</SwitchLabel>
        <ToggleSwitch
          id="repetition"
          on={toggleRepetition}
          off={toggleRepetition}
          defaultToggle={isRepeat}
        />
      </SwitchWrapper>
      <Spacing direction="vertical" size={10} />
      <KillingPartInfo killingPart={killingPart} />
      <SRAlert>{`${killingRank === 4 ? '전체' : `${killingRank}등 킬링파트`} 재생`}</SRAlert>
    </Wrapper>
  );
};

export default SongPage;
