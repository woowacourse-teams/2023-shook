import styled from 'styled-components';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import { usePostKillingPart } from '@/features/songs/remotes/usePostKillingPart';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { toPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';

const RegisterPart = () => {
  const { isOpen, openModal, closeModal } = useModal();
  const { showToast } = useToastContext();
  const { user } = useAuthContext();
  const { interval, partStartTime, songId, songVideoId } = useVoteInterfaceContext();
  const { createKillingPart } = usePostKillingPart();
  const { videoPlayer } = useVideoPlayerContext();

  const submitKillingPart = async () => {
    videoPlayer.current?.pauseVideo();
    await createKillingPart(songId, { startSecond: partStartTime, length: interval });
    openModal();
  };

  const voteTimeText = toPlayingTimeText(partStartTime, partStartTime + interval);

  const copyPartVideoUrl = async () => {
    await copyClipboard(`https://www.youtube.com/watch?v=${songVideoId}`);
    closeModal();
    showToast('클립보드에 영상링크가 복사되었습니다.');
  };

  return (
    <>
      <RegisterButton onClick={submitKillingPart}>등록</RegisterButton>
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>
          <TitleColumn>{user?.nickname}님의</TitleColumn>
          <TitleColumn>킬링파트 등록을 완료했습니다.</TitleColumn>
        </ModalTitle>
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
    </>
  );
};

export default RegisterPart;

const RegisterButton = styled.button`
  width: 100%;
  margin-top: 8px;
  padding: 8px 11px;

  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};
  letter-spacing: 6px;

  background-color: ${({ theme: { color } }) => color.primary};
  border-radius: 6px;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    position: static;
    left: unset;
    transform: unset;

    width: 100%;
    padding: 11px 15px;

    font-size: 18px;
  }
`;

const ModalTitle = styled.h3``;

const TitleColumn = styled.div`
  text-align: center;
`;

const ModalContent = styled.div`
  padding: 16px 0;

  font-size: 16px;
  color: #b5b3bc;
  text-align: center;
  white-space: pre-line;
`;

const Message = styled.div``;

const Button = styled.button`
  height: 36px;
  color: ${({ theme: { color } }) => color.white};
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
