import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import dummyJacket from '@/assets/image/album-jacket.png';
import useToastContext from '@/components/@common/Toast/hooks/useToastContext';
import { IntervalInput } from '@/components/IntervalInput';
import useKillingPartInterval from '@/components/KillingPartToggleGroup/hooks/useKillingPartInterval';
import KillingPartToggleGroup from '@/components/KillingPartToggleGroup/KillingPartToggleGroup';
import useModal from '@/components/Modal/hooks/useModal';
import Modal from '@/components/Modal/Modal';
import { VideoSlider } from '@/components/VideoSlider';
import Youtube from '@/components/Youtube/Youtube';
import { usePostKillingPart } from '@/hooks/killingPart';
import { useGetSongDetail } from '@/hooks/song';
import { minSecToSeconds } from '@/utils/convertTime';
import {
  Confirm,
  Container,
  Flex,
  ModalContent,
  ModalTitle,
  Register,
  RegisterTitle,
  Share,
  Singer,
  Jacket,
  SongTitle,
  Spacing,
  SongInfoContainer,
  Info,
} from './SongDetailPage.style';
import type { TimeMinSec } from '@/components/IntervalInput/IntervalInput.type';
import type { PartVideoUrl } from '@/types/killingPart';

// TODO: 분리
const getResultMessage = (rank: number | undefined) => {
  switch (rank) {
    case 1: {
      return '축하합니다. 사람들이 가장 좋아하는 킬링파트입니다!🎉\n친구들에게 킬링파트를 공유해보세요!';
    }
    case 2: {
      return '두 번째로 인기 많은 킬링파트입니다!🎉\n친구들에게 킬링파트를 공유해보세요!';
    }
    case 3: {
      return '세 번째로 인기 많은 킬링파트입니다!🎉\n친구들에게 킬링파트를 공유해보세요!';
    }

    default: {
      return '자신의 파트가 투표되었어요!🎉\n등록한 파트를 친구들에게 공유해보세요!';
    }
  }
};

const SongDetailPage = () => {
  const { id: newId } = useParams();

  const [player, setPlayer] = useState<YT.Player | undefined>();
  const [errorMessage, setErrorMessage] = useState('');
  const [partStart, setPartStart] = useState<TimeMinSec>({ minute: 0, second: 0 });

  const { isOpen, openModal, closeModal } = useModal();
  const { interval, setKillingPartInterval } = useKillingPartInterval();
  const { songDetail } = useGetSongDetail(Number(newId));
  const { killingPartPostResponse, createKillingPart } = usePostKillingPart();
  const { showToast } = useToastContext();

  useEffect(() => {
    if (player?.getPlayerState() === 2) player.playVideo();

    const timer = window.setInterval(() => {
      const startSecond = minSecToSeconds([partStart.minute, partStart.second]);

      player?.seekTo(startSecond, true);
    }, interval * 1000);

    return () => window.clearInterval(timer);
  }, [player, partStart, interval]);

  if (!newId) return;
  if (!songDetail) return;

  const isActiveSubmission = errorMessage.length === 0;

  const onChangeErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  const onChangePartStart = (name: string, value: number) => {
    setPartStart({
      ...partStart,
      [name]: Number(value),
    });
  };

  const { id, title, singer, videoLength, videoUrl } = songDetail;
  const videoId = videoUrl.replace('https://youtu.be/', '');

  const submitKillingPart = async () => {
    player?.pauseVideo();

    const startSecond = minSecToSeconds([partStart.minute, partStart.second]);

    await createKillingPart(id, { startSecond, length: interval });

    openModal();
  };

  const copyUrlClipboard = async (partVideoUrl: PartVideoUrl | undefined) => {
    if (!partVideoUrl) return;

    try {
      await navigator.clipboard.writeText(partVideoUrl);
    } catch {
      const el = document.createElement('textarea');
      el.value = partVideoUrl;

      document.body.appendChild(el);
      el.select();
      document.execCommand('copy');
      document.body.removeChild(el);
    }

    showToast('클립보드에 영상링크가 복사되었습니다.');
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
      <Youtube videoId={videoId} start={0} onReady={({ target }) => setPlayer(target)} />
      <Spacing direction="vertical" size={20} />
      <RegisterTitle>당신의 킬링파트에 투표하세요🎧</RegisterTitle>
      <Spacing direction="vertical" size={20} />
      <KillingPartToggleGroup interval={interval} setKillingPartInterval={setKillingPartInterval} />
      <Spacing direction="vertical" size={40} />
      <IntervalInput
        videoLength={videoLength}
        errorMessage={errorMessage}
        partStart={partStart}
        interval={interval}
        onChangeErrorMessage={onChangeErrorMessage}
        onChangePartStart={onChangePartStart}
      />
      <VideoSlider
        time={minSecToSeconds([partStart.minute, partStart.second])}
        interval={interval}
        videoLength={videoLength}
        setPartStart={(timeMinSec: TimeMinSec) => setPartStart(timeMinSec)}
        player={player}
      />
      <Spacing direction="vertical" size={20} />
      <Spacing direction="vertical" size={40} />
      <Register disabled={!isActiveSubmission} type="button" onClick={submitKillingPart}>
        등록
      </Register>
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트에 투표했습니다.</ModalTitle>
        <Spacing direction="vertical" size={12} />
        <ModalContent>
          <div>{getResultMessage(killingPartPostResponse?.rank)}</div>
          <Spacing direction="vertical" size={4} />
          <div>현재까지 총 {killingPartPostResponse?.voteCount}표를 받았습니다.</div>
        </ModalContent>
        <Spacing direction="vertical" size={16} />
        <Flex>
          <Confirm type="button" onClick={closeModal}>
            확인
          </Confirm>
          <Spacing direction="horizontal" size={12} />
          <Share
            type="button"
            onClick={() => copyUrlClipboard(killingPartPostResponse?.partVideoUrl)}
          >
            공유하기
          </Share>
        </Flex>
      </Modal>
    </Container>
  );
};

export default SongDetailPage;
