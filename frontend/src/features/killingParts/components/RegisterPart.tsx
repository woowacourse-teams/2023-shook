import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import { usePostKillingPart } from '@/features/killingParts/remotes/usePostKillingPart';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';
import { toPlayingTimeText } from '@/shared/utils/convertTime';

const RegisterPart = () => {
  const { isOpen, openModal, closeModal } = useModal();
  const { user } = useAuthContext();
  const { interval, partStartTime, songId } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const { createKillingPart } = usePostKillingPart();
  const navigate = useNavigate();

  // 현재 useMutation 훅이 response 객체를 리턴하지 않고 내부적으로 처리합니다.
  // 때문에 컴포넌트 단에서 createKillingPart 성공 여부에 따라 등록 완료 만료를 처리를 할 수 없어요!
  // 현재 비로그인 시에 등록을 누르면 두 개의 모달이 뜹니다.정
  const submitKillingPart = async () => {
    video.pause();
    await createKillingPart(songId, { startSecond: partStartTime, length: interval });
    navigate(-1);
  };

  const voteTimeText = toPlayingTimeText(partStartTime, partStartTime + interval);

  return (
    <>
      <RegisterButton type="submit" onClick={openModal}>
        등록
      </RegisterButton>
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <ModalTitle>
          <TitleColumn>{user?.nickname}님의 파트 저장</TitleColumn>
        </ModalTitle>
        <ModalContent>
          <Message>
            <Part>{voteTimeText}</Part>
          </Message>
          <Spacing direction="vertical" size={6} />
          <Message>나만의 파트로 등록하시겠습니까?</Message>
        </ModalContent>
        <ButtonContainer>
          <Confirm type="button" onClick={closeModal}>
            취소
          </Confirm>
          <Share type="button" onClick={submitKillingPart}>
            등록
          </Share>
        </ButtonContainer>
      </Modal>
    </>
  );
};

export default RegisterPart;

const RegisterButton = styled.button`
  width: 100%;
  margin-top: auto;
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

const Part = styled.span`
  padding: 6px 11px;

  color: white;
  letter-spacing: 1px;

  background-color: ${({ theme: { color } }) => color.disabled};
  border-radius: 10px;
`;
