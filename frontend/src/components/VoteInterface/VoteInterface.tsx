import { useState } from 'react';
import useVideoPlayerContext from '@/context/useVideoPlayerContext';
import useVoteInterfaceContext from '@/context/useVoteInterfaceContext';
import { usePostKillingPart } from '@/hooks/killingPart';
import { ButtonContainer } from '@/pages/SongDetailPage.style';
import { minSecToSeconds } from '@/utils/convertTime';
import useToastContext from '../@common/Toast/hooks/useToastContext';
import { IntervalInput } from '../IntervalInput';
import KillingPartToggleGroup from '../KillingPartToggleGroup';
import useModal from '../Modal/hooks/useModal';
import Modal from '../Modal/Modal';
import { VideoSlider } from '../VideoSlider';
import {
  Confirm,
  Container,
  ModalContent,
  ModalTitle,
  Register,
  RegisterTitle,
  Share,
} from './VoteInterface.style';
import type { PartVideoUrl } from '@/types/killingPart';

interface VoteInterfaceProps {
  videoLength: number;
  songId: number;
}

const VoteInterface = ({ videoLength, songId }: VoteInterfaceProps) => {
  const { showToast } = useToastContext();
  const { interval, partStartTime } = useVoteInterfaceContext();
  const { videoPlayer } = useVideoPlayerContext();
  const { killingPartPostResponse, createKillingPart } = usePostKillingPart();
  const { isOpen, openModal, closeModal } = useModal();

  // TODO: 에러메시지 길이로 등록가능 상태 판단하는 로직 개선 및 상태 IntervalInput 컴포넌트로 이동
  const [errorMessage, setErrorMessage] = useState('');
  const isActiveSubmission = errorMessage.length === 0;

  const updateErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

  const submitKillingPart = async () => {
    videoPlayer?.pauseVideo();

    const startSecond = minSecToSeconds([partStartTime.minute, partStartTime.second]);

    await createKillingPart(songId, { startSecond, length: interval });

    openModal();
  };

  // TODO: 우코 분리 로직과 충돌해결 및 병합 및 옵셔널 처리
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
      <RegisterTitle>당신의 킬링파트에 투표하세요🎧</RegisterTitle>
      <KillingPartToggleGroup />
      <IntervalInput
        videoLength={videoLength}
        errorMessage={errorMessage}
        onChangeErrorMessage={updateErrorMessage}
      />
      <VideoSlider videoLength={videoLength} />
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
    </Container>
  );
};

export default VoteInterface;
