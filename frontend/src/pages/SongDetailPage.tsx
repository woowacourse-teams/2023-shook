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
  ButtonContainer,
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

const SongDetailPage = () => {
  const { id: newId } = useParams();

  const [player, setPlayer] = useState<YT.Player | undefined>();
  const [errorMessage, setErrorMessage] = useState('');
  const [partStart, setPartStart] = useState<TimeMinSec>({ minute: 0, second: 0 });

  const { isOpen, openModal, closeModal } = useModal();
  const { interval, setKillingPartInterval } = useKillingPartInterval();
  const { killingPartPostResponse, createKillingPart } = usePostKillingPart();
  const { songDetail } = useGetSongDetail(Number(newId));
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

  const { id, title, singer, videoLength, songVideoUrl } = songDetail;
  const videoId = songVideoUrl.replace('https://youtu.be/', '');

  const isActiveSubmission = errorMessage.length === 0;

  const onChangeErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  const onChangePartStart = (name: string, value: number) => {
    setPartStart({ ...partStart, [name]: value });
  };

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
        partStartTime={partStart}
        interval={interval}
        onChangeErrorMessage={onChangeErrorMessage}
        onChangePartStartTime={onChangePartStart}
      />
      <VideoSlider
        partStartTime={minSecToSeconds([partStart.minute, partStart.second])}
        interval={interval}
        videoLength={videoLength}
        setPartStartTime={(timeMinSec: TimeMinSec) => setPartStart(timeMinSec)}
        player={player}
      />
      <Spacing direction="vertical" size={40} />
      <Register disabled={!isActiveSubmission} type="button" onClick={submitKillingPart}>
        등록
      </Register>
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트 투표를 완료했습니다.</ModalTitle>
        <ModalContent>컨텐츠는 아직 없습니다.</ModalContent>
        <Spacing direction="vertical" size={16} />
        <ButtonContainer>
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
        </ButtonContainer>
      </Modal>
    </Container>
  );
};

export default SongDetailPage;
