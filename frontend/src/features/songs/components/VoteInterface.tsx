import { styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import VideoSlider from '@/features/youtube/components/VideoSlider';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { getPlayingTimeText, minSecToSeconds } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import { usePostKillingPart } from '../remotes/usePostKillingPart';
import KillingPartToggleGroup from './KillingPartToggleGroup';

const VoteInterface = () => {
  const { showToast } = useToastContext();
  const { interval, partStartTime, songId } = useVoteInterfaceContext();
  const { videoPlayer } = useVideoPlayerContext();
  const { killingPartPostResponse, createKillingPart } = usePostKillingPart();
  const { isOpen, openModal, closeModal } = useModal();

  const startSecond = minSecToSeconds([partStartTime.minute, partStartTime.second]);
  const voteTimeText = getPlayingTimeText(startSecond, startSecond + interval);

  const submitKillingPart = async () => {
    videoPlayer?.pauseVideo();

    const startSecond = minSecToSeconds([partStartTime.minute, partStartTime.second]);

    await createKillingPart(songId, { startSecond, length: interval });

    openModal();
  };

  const copyPartVideoUrl = async () => {
    if (!killingPartPostResponse?.partVideoUrl) return;

    await copyClipboard(killingPartPostResponse?.partVideoUrl);
    closeModal();
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  return (
    <Container>
      <RegisterTitle>당신의 킬링파트에 투표하세요 🔖</RegisterTitle>
      <KillingPartToggleGroup />
      <VideoSlider />
      <Register type="button" onClick={submitKillingPart}>
        투표
      </Register>

      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트 투표를 완료했습니다.</ModalTitle>
        <ModalContent>
          <Message>{voteTimeText}</Message>
          <Message>파트를 공유해 보세요😀</Message>
        </ModalContent>
        <ButtonContainer>
          <Confirm type="button" onClick={closeModal}>
            확인
          </Confirm>
          <Share type="button" onClick={copyPartVideoUrl}>
            공유하기
          </Share>
        </ButtonContainer>
      </Modal>
    </Container>
  );
};

export default VoteInterface;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

const RegisterTitle = styled.p`
  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;

const Register = styled.button`
  width: 100%;
  height: 36px;
  border: none;
  border-radius: 10px;
  background-color: ${({ theme: { color } }) => color.primary};
  color: ${({ theme: { color } }) => color.white};
  cursor: pointer;
`;

const ModalTitle = styled.h3``;

const ModalContent = styled.div`
  font-size: 16px;
  color: #b5b3bc;
  white-space: pre-line;
  padding: 16px 0;
  text-align: center;
`;

const Message = styled.div``;

const Button = styled.button`
  height: 36px;
  border: none;
  border-radius: 10px;
  color: ${({ theme: { color } }) => color.white};
  cursor: pointer;
`;

const Confirm = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.secondary};
`;

const Share = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.primary};
`;

const ButtonContainer = styled.div`
  display: flex;
  width: 100%;
  gap: 16px;
`;
