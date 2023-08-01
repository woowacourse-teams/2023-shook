import { useState } from 'react';
import { usePostKillingPart } from '@/hooks/killingPart';
import { ButtonContainer } from '@/pages/SongDetailPage.style';
import { minSecToSeconds } from '@/utils/convertTime';
import useToastContext from '../@common/Toast/hooks/useToastContext';
import { IntervalInput } from '../IntervalInput';
import KillingPartToggleGroup from '../KillingPartToggleGroup';
import useKillingPartInterval from '../KillingPartToggleGroup/hooks/useKillingPartInterval';
import useModal from '../Modal/hooks/useModal';
import Modal from '../Modal/Modal';
import { VideoSlider } from '../VideoSlider';
import {
  Confirm,
  ModalContent,
  ModalTitle,
  Register,
  RegisterTitle,
  Share,
} from './VoteInterface.style';
import type { TimeMinSec } from '../IntervalInput/IntervalInput.type';
import type { PartVideoUrl } from '@/types/killingPart';

interface VoteInterfaceProps {
  videoLength: number;
  videoPlayer: YT.Player | undefined;
}

const VoteInterface = ({ videoLength, videoPlayer }: VoteInterfaceProps) => {
  const { showToast } = useToastContext();
  const { killingPartPostResponse, createKillingPart } = usePostKillingPart();
  const { interval, setKillingPartInterval } = useKillingPartInterval();
  const { isOpen, openModal, closeModal } = useModal();

  const [errorMessage, setErrorMessage] = useState('');
  const [partStartTime, setPartStartTime] = useState<TimeMinSec>({ minute: 0, second: 0 });

  const isActiveSubmission = errorMessage.length === 0;
  const partStartTimeInSeconds = minSecToSeconds([partStartTime.minute, partStartTime.second]);

  const updatePartStartTime = (name: string, value: number) => {
    setPartStartTime({ ...partStartTime, [name]: value });
  };

  const updateErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  const submitKillingPart = async () => {
    // player?.pauseVideo();

    const startSecond = minSecToSeconds([partStartTime.minute, partStartTime.second]);

    await createKillingPart(1, { startSecond, length: interval });

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
    <>
      <RegisterTitle>당신의 킬링파트에 투표하세요🎧</RegisterTitle>
      <KillingPartToggleGroup interval={interval} setKillingPartInterval={setKillingPartInterval} />
      <IntervalInput
        interval={interval}
        partStartTime={partStartTime}
        videoLength={videoLength}
        errorMessage={errorMessage}
        onChangePartStartTime={updatePartStartTime}
        onChangeErrorMessage={updateErrorMessage}
      />
      <VideoSlider
        interval={interval}
        partStartTime={partStartTimeInSeconds}
        setPartStartTime={setPartStartTime}
        videoLength={videoLength}
        player={videoPlayer}
      />
      <Register disabled={!isActiveSubmission} type="button" onClick={submitKillingPart}>
        등록
      </Register>

      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트 투표를 완료했습니다.</ModalTitle>
        <ModalContent>컨텐츠는 아직 없습니다.</ModalContent>
        <ButtonContainer>
          <Confirm type="button" onClick={closeModal}>
            확인
          </Confirm>
          <Share
            type="button"
            onClick={() => copyUrlClipboard(killingPartPostResponse?.partVideoUrl)}
          >
            공유하기
          </Share>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default VoteInterface;
