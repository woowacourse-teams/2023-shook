import { styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import VideoSlider from '@/features/youtube/components/VideoSlider';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { toPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import { usePostKillingPart } from '../remotes/usePostKillingPart';
import KillingPartToggleGroup from './KillingPartToggleGroup';

const VoteInterface = () => {
  const { showToast } = useToastContext();
  const { interval, partStartTime, songId, songVideoId } = useVoteInterfaceContext();
  const { videoPlayer } = useVideoPlayerContext();

  const { createKillingPart } = usePostKillingPart();
  const { isOpen, openModal, closeModal } = useModal();

  const voteTimeText = toPlayingTimeText(partStartTime, partStartTime + interval);

  const submitKillingPart = async () => {
    videoPlayer.current?.pauseVideo();
    await createKillingPart(songId, { startSecond: partStartTime, length: interval });
    openModal();
  };

  const copyPartVideoUrl = async () => {
    await copyClipboard(`https://www.youtube.com/watch?v=${songVideoId}`);
    closeModal();
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  return (
    <Container>
      <RegisterTitle>당신의 킬링파트를 등록하세요</RegisterTitle>
      <Spacing direction="vertical" size={16} />
      <KillingPartToggleGroup />
      <Spacing direction="vertical" size={24} />
      <VideoSlider />
      <Spacing direction="vertical" size={16} />
      <Register type="button" onClick={submitKillingPart}>
        등록
      </Register>

      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>킬링파트 등록을 완료했습니다.</ModalTitle>
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
  cursor: pointer;

  width: 100%;
  height: 36px;

  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.primary};
  border: none;
  border-radius: 10px;
`;

const ModalTitle = styled.h3``;

const ModalContent = styled.div`
  padding: 16px 0;

  font-size: 16px;
  color: #b5b3bc;
  text-align: center;
  white-space: pre-line;
`;

const Message = styled.div``;

const Button = styled.button`
  cursor: pointer;

  height: 36px;

  color: ${({ theme: { color } }) => color.white};

  border: none;
  border-radius: 10px;
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
  gap: 16px;
  width: 100%;
`;
