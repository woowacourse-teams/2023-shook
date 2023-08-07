import { useState } from 'react';
import { Link } from 'react-router-dom';
import { styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import VideoSlider from '@/features/youtube/components/VideoSlider';
import useVideoPlayerContext from '@/features/youtube/contexts/useVideoPlayerContext';
import { getPlayingTimeText, minSecToSeconds } from '@/shared/utils/convertTime';
import copyClipboard from '@/shared/utils/copyClipBoard';
import useModal from '../../../shared/components/Modal/hooks/useModal';
import Modal from '../../../shared/components/Modal/Modal';
import useToastContext from '../../../shared/components/Toast/hooks/useToastContext';
import { usePostKillingPart } from '../hooks/usePostKillingPart';
import IntervalInput from './IntervalInput';
import KillingPartToggleGroup from './KillingPartToggleGroup';

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

  const startSecond = minSecToSeconds([partStartTime.minute, partStartTime.second]);
  const voteTimeText = getPlayingTimeText(startSecond, startSecond + interval);

  const updateErrorMessage = (message: string) => {
    setErrorMessage(message);
  };

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
      <SubTitle>
        <Link to={`/song/${songId}`}>
          <PrimarySpan>킬링파트</PrimarySpan> 듣기
        </Link>
        <UnderLine>
          <PrimarySpan>킬링파트</PrimarySpan> 투표
        </UnderLine>
      </SubTitle>
      <RegisterTitle>당신의 킬링파트에 투표하세요 🔖</RegisterTitle>
      <KillingPartToggleGroup />
      <IntervalInput
        videoLength={videoLength}
        errorMessage={errorMessage}
        onChangeErrorMessage={updateErrorMessage}
      />
      <VideoSlider videoLength={videoLength} />
      <Register disabled={!isActiveSubmission} type="button" onClick={submitKillingPart}>
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

const Register = styled.button<{ disabled: boolean }>`
  width: 100%;
  height: 36px;
  border: none;
  border-radius: 10px;
  background-color: ${({ disabled, theme: { color } }) => {
    return disabled ? color.disabledBackground : color.primary;
  }};

  color: ${({ disabled, theme: { color } }) => {
    return disabled ? color.disabled : color.white;
  }};

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

const UnderLine = styled.div`
  border-bottom: 2px solid white;
`;

const SubTitle = styled.h2`
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};
`;

const PrimarySpan = styled.span`
  color: ${({ theme: { color } }) => color.primary};
`;

const ButtonContainer = styled.div`
  display: flex;
  width: 100%;
  gap: 16px;
`;
