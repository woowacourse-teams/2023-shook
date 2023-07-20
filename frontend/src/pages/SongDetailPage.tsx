import { useEffect, useState } from 'react';
import useToastContext from '@/components/@common/Toast/hooks/useToastContext';
import { IntervalInput } from '@/components/IntervalInput';
import useKillingPartInterval from '@/components/KillingPartToggleGroup/hooks/useKillingPartInterval';
import KillingPartToggleGroup from '@/components/KillingPartToggleGroup/KillingPartToggleGroup';
import useModal from '@/components/Modal/hooks/useModal';
import Modal from '@/components/Modal/Modal';
import { VideoSlider } from '@/components/VideoSlider';
import Youtube from '@/components/Youtube/Youtube';
import { minSecToSeconds } from '@/utils/convertTime';
import {
  Confirm,
  Flex,
  ModalContent,
  ModalTitle,
  Register,
  Share,
  Singer,
  SongTitle,
  Spacing,
} from './SongDetailPage.style';
import type { TimeMinSec } from '@/components/IntervalInput/IntervalInput.type';

const response = {
  title: 'Super Shy',
  singer: 'NewJeans',
  videoLength: 201,
  videoUrl: 'https://www.youtube.com/ArmDp-zijuc',
};

// TODO: 분리
const getResultMessage = (rank: number) => {
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
  const { isOpen, openModal, closeModal } = useModal();
  const [player, setPlayer] = useState<YT.Player | undefined>();
  const [errorMessage, setErrorMessage] = useState('');
  const [partStart, setPartStart] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const { interval, setKillingPartInterval } = useKillingPartInterval();
  const { showToast } = useToastContext();
  const [modalContent, setModalContent] = useState('임시입니다.');

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

  const copyUrlClipboard = async () => {
    const start = minSecToSeconds([partStart.minute, partStart.second]);
    const end = start + interval;

    try {
      await navigator.clipboard.writeText(
        `https://www.youtube.com/embed/ArmDp-zijuc?start=${start}&end=${end}`
      );
    } catch {
      const el = document.createElement('textarea');
      el.value = `https://www.youtube.com/embed/ArmDp-zijuc?start=${start}&end=${end}`;

      document.body.appendChild(el);
      el.select();
      document.execCommand('copy');
      document.body.removeChild(el);
    }

    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  const submitKillingPart = () => {
    player?.pauseVideo();
    // response => rank
    const rank = 1;
    setModalContent(getResultMessage(rank));
    openModal();
  };

  const [videoInfo, setVideoInfo] = useState<{
    title: string;
    singer: string;
    videoLength: number;
  }>({ title: '', singer: '', videoLength: 0 });

  useEffect(() => {
    const { title, singer, videoLength } = response;
    // const embedUrl = videoUrl.replace('https://www.youtube.com', 'https://www.youtube.com/embed');

    setVideoInfo({ title, singer, videoLength });
  }, []);

  const { title, singer, videoLength } = videoInfo;

  return (
    <div>
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <Youtube videoId="ArmDp-zijuc" start={0} onReady={({ target }) => setPlayer(target)} />
      <div style={{ color: 'white' }}>당신의 킬링파트에 투표하세요🎧</div>
      <Spacing direction="vertical" size={20} />
      <KillingPartToggleGroup interval={interval} setKillingPartInterval={setKillingPartInterval} />
      <Spacing direction="vertical" size={20} />
      <VideoSlider
        time={minSecToSeconds([partStart.minute, partStart.second])}
        interval={interval}
        videoLength={videoLength}
        setPartStart={(timeMinSec: TimeMinSec) => setPartStart(timeMinSec)}
        player={player}
      />
      <Spacing direction="vertical" size={20} />
      <IntervalInput
        videoLength={videoLength}
        errorMessage={errorMessage}
        partStart={partStart}
        interval={interval}
        onChangeErrorMessage={onChangeErrorMessage}
        onChangePartStart={onChangePartStart}
      />

      <Spacing direction="vertical" size={40} />

      <Register disabled={!isActiveSubmission} type="button" onClick={submitKillingPart}>
        등록
      </Register>

      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트에 투표했습니다.</ModalTitle>
        <Spacing direction="vertical" size={12} />
        <ModalContent>
          <div>{modalContent}</div>
        </ModalContent>
        <Spacing direction="vertical" size={16} />
        <Flex>
          <Confirm type="button" onClick={closeModal}>
            확인
          </Confirm>
          <Spacing direction="horizontal" size={12} />
          <Share type="button" onClick={copyUrlClipboard}>
            공유하기
          </Share>
        </Flex>
      </Modal>
    </div>
  );
};

export default SongDetailPage;
