import { styled } from 'styled-components';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import PartIntervalController from '@/features/songs/components/PartIntervalController';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import WaveScrubber from '@/features/youtube/components/WaveScrubber';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';
import useToastContext from '@/shared/components/Toast/hooks/useToastContext';
import { toPlayingTimeText } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import { usePostKillingPart } from '../remotes/usePostKillingPart';

const VoteInterface = () => {
  const { showToast } = useToastContext();
  const { interval, partStartTime, songId, songVideoId } = useVoteInterfaceContext();
  const { videoPlayer } = useVideoPlayerContext();

  const { createKillingPart } = usePostKillingPart();
  const { isOpen, openModal, closeModal } = useModal();

  const { user } = useAuthContext();

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
      <RegisterTitle>나만의 파트 저장하기</RegisterTitle>
      <Warning>같은 파트에 대한 중복 등록은 한 번의 등록으로 처리됩니다.</Warning>
      <Spacing direction="vertical" size={4} />
      <PartIntervalController />
      <Spacing direction="vertical" size={8} />
      <WaveScrubber />
      <FlexibleSpacing />
      <Register type="button" onClick={submitKillingPart}>
        등록
      </Register>
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
    </Container>
  );
};

export default VoteInterface;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 320px;
    padding: 16px;
  }
`;

const RegisterTitle = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 22px;
  }
`;

const Register = styled.button`
  width: 92%;

  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.primary};
  padding: 6px 11px;

  letter-spacing: 6px;

  border-radius: 10px;

  margin-top: 8px;
  position: fixed;
  left: 50%;
  transform: translate(-50%, 0);
  bottom: 8px;

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 100%;
    left: unset;
    transform: unset;
    position: static;

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

const Warning = styled.div`
  font-size: 14px;
  color: ${({ theme: { color } }) => color.subText};

  @media (min-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;

const FlexibleSpacing = styled.div`
  
  @media (min-width:  ${({theme}) => theme.breakPoints.md}) {
    margin: 8px 0;
    flex: 1;  
  }
`;
