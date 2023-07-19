import { useState } from 'react';
import useToastContext from '@/components/@common/Toast/hooks/useToastContext';
import { IntervalInput } from '@/components/IntervalInput';
import useKillingPartInterval from '@/components/KillingPartToggleGroup/hooks/useKillingPartInterval';
import KillingPartToggleGroup from '@/components/KillingPartToggleGroup/KillingPartToggleGroup';
import useModal from '@/components/Modal/hooks/useModal';
import Modal from '@/components/Modal/Modal';
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

// mock_data
// const songId = 1;
const videoLength = 200;
// const videoUrl = 'https://www.youtube.com/embed/ArmDp-zijuc';
const title = 'Super Shy';
const singer = 'NewJeans';

const SongDetailPage = () => {
  const { isOpen, openModal, closeModal } = useModal();
  const [errorMessage, setErrorMessage] = useState('');
  const [partStart, setPartStart] = useState<TimeMinSec>({ minute: 0, second: 0 });
  const { interval, setKillingPartInterval } = useKillingPartInterval();
  const { showToast } = useToastContext();

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
    try {
      await navigator.clipboard.writeText('https://www.youtube.com/embed/ArmDp-zijuc');
    } catch {
      const el = document.createElement('textarea');
      el.value = 'https://www.youtube.com/embed/ArmDp-zijuc!';
      document.body.appendChild(el);
      el.select();
      document.execCommand('copy');
      document.body.removeChild(el);
    }

    closeModal();
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  const submitKillingPart = () => {
    openModal();
  };

  return (
    <div>
      <SongTitle>{title}</SongTitle>
      <Singer>{singer}</Singer>
      <KillingPartToggleGroup interval={interval} setKillingPartInterval={setKillingPartInterval} />
      <IntervalInput
        videoLength={videoLength}
        errorMessage={errorMessage}
        partStart={partStart}
        interval={interval}
        onChangeErrorMessage={onChangeErrorMessage}
        onChangePartStart={onChangePartStart}
      />
      <Register disabled={!isActiveSubmission} type="button" onClick={submitKillingPart}>
        등록
      </Register>

      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트에 투표했습니다.</ModalTitle>
        <ModalContent>로고 색으로 좋아요!</ModalContent>
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
