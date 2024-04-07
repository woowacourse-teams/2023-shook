import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import useVideoPlayerContext from '@/features/youtube/hooks/useVideoPlayerContext';
import { useConfirmContext } from '@/shared/components/ConfirmModal/hooks/useConfirmContext';
import Spacing from '@/shared/components/Spacing';
import { useMutation } from '@/shared/hooks/useMutation';
import { toPlayingTimeText } from '@/shared/utils/convertTime';
import { postKillingPart } from '../remotes/killingPart';

const RegisterPart = () => {
  const { user } = useAuthContext();
  const { interval, partStartTime, songId } = useCollectingPartContext();
  const video = useVideoPlayerContext();
  const { confirmPopup } = useConfirmContext();
  const voteTimeText = toPlayingTimeText(partStartTime, partStartTime + interval);
  const { mutateData: createKillingPart } = useMutation(postKillingPart);
  const navigate = useNavigate();

  // 현재 useMutation 훅이 response 객체를 리턴하지 않고 내부적으로 처리합니다.
  // 때문에 컴포넌트 단에서 createKillingPart 성공 여부에 따라 등록 완료 만료를 처리를 할 수 없어요!
  // 현재 비로그인 시에 등록을 누르면 두 개의 모달이 뜹니다.
  const submitKillingPart = async () => {
    video.pause();

    const isConfirmed = await confirmPopup({
      title: `${user?.nickname}님의 파트 저장`,
      content: (
        <ContentContainer>
          <Spacing direction="vertical" size={10} />
          <Part>{voteTimeText}</Part>
          <Spacing direction="vertical" size={10} />
          <Message>나만의 파트로 등록하시겠습니까?</Message>
        </ContentContainer>
      ),
    });

    if (isConfirmed) {
      await createKillingPart(songId, { startSecond: partStartTime, length: interval });
      navigate(-1);
    }
  };

  return <RegisterButton onClick={submitKillingPart}>등록</RegisterButton>;
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
    padding: 11px 15px;
    font-size: 18px;
  }
`;

const Message = styled.div``;

const Part = styled.span`
  padding: 6px 11px;

  color: white;
  letter-spacing: 1px;

  background-color: ${({ theme: { color } }) => color.disabled};
  border-radius: 10px;
`;

const ContentContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
`;
