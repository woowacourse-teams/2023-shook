import { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import NicknameChangingModal from '@/features/member/components/NicknameChangingModal';
import WithdrawalModal from '@/features/member/components/WithdrawalModal';
import { deleteMember } from '@/features/member/remotes/member';
import { updateNickname } from '@/features/member/remotes/nickname';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';

const EditProfilePage = () => {
  const { user, logout } = useAuthContext();
  const [nicknameEntered, setNicknameEntered] = useState(user?.nickname);
  const [errorMessage, setErrorMessage] = useState('이전과 다른 닉네임으로 변경해주세요.');
  const navigate = useNavigate();
  const {
    isOpen: isWithdrawalModalOpen,
    openModal: openWithdrawalModal,
    closeModal: closeWithdrawalModal,
  } = useModal();
  const {
    isOpen: isNicknameModalOpen,
    openModal: openNicknameModal,
    closeModal: closeNicknameModal,
  } = useModal();

  const hasError = errorMessage.length !== 0;

  // 회원탈퇴 API
  const { mutateData: withdrawMember } = useMutation(deleteMember(user?.memberId));

  const handleWithdrawal = async () => {
    await withdrawMember();
    logout();
    navigate(ROUTE_PATH.ROOT);
  };

  // 닉네임변경 API
  const mutateNickname = useMemo(
    () => updateNickname(user?.memberId, nicknameEntered),
    [nicknameEntered, user?.memberId]
  );
  const { mutateData: changeNickname } = useMutation(mutateNickname);

  if (!user) {
    navigate(ROUTE_PATH.LOGIN);
    return;
  }
  const handleChangeNickname: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    const currentNickname = event.currentTarget.value;
    setNicknameEntered(currentNickname);
    if (currentNickname.length < 2 || currentNickname.length > 10) {
      setErrorMessage('2글자 이상 10글자 이하 문자만 가능합니다.');
    } else if (currentNickname === user.nickname) {
      setErrorMessage('이전과 다른 닉네임으로 변경해주세요.');
    } else {
      setErrorMessage('');
    }
  };

  const submitNicknameChanged = async () => {
    await changeNickname();
    logout();
    navigate(ROUTE_PATH.LOGIN);
  };

  return (
    <Container>
      <Title>프로필 수정</Title>
      <Spacing direction={'vertical'} size={100} />
      <Avatar src={shookshook} />
      <Label htmlFor="nickname">닉네임</Label>
      <Spacing direction={'vertical'} size={4} />
      <Input
        id="nickname"
        value={nicknameEntered}
        onChange={handleChangeNickname}
        autoComplete="off"
      />
      <Spacing direction={'vertical'} size={8} />
      {hasError && <BottomError>{errorMessage}</BottomError>}
      <Spacing direction={'vertical'} size={16} />
      <WithdrawalButton onClick={openWithdrawalModal}>회원 탈퇴</WithdrawalButton>
      <SubmitButton onClick={openNicknameModal} disabled={hasError}>
        변경 하기
      </SubmitButton>
      <WithdrawalModal
        isOpen={isWithdrawalModalOpen}
        closeModal={closeWithdrawalModal}
        onWithdraw={handleWithdrawal}
      />
      <NicknameChangingModal
        isOpen={isNicknameModalOpen}
        closeModal={closeNicknameModal}
        onSubmitNickname={submitNicknameChanged}
        nickname={nicknameEntered}
      />
    </Container>
  );
};

export default EditProfilePage;

const Container = styled.div`
  position: relative;

  display: flex;
  flex-direction: column;

  width: 100%;
  min-width: 300px;
  max-width: 800px;
  height: calc(100vh - ${({ theme: { headerHeight } }) => headerHeight.desktop});
  margin: auto 0;
  padding-top: ${({ theme: { headerHeight } }) => headerHeight.desktop};

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    height: calc(100vh - ${({ theme: { headerHeight } }) => headerHeight.mobile});
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.mobile};
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    height: calc(100vh - ${({ theme: { headerHeight } }) => headerHeight.xxs});
    padding-top: ${({ theme: { headerHeight } }) => headerHeight.xxs};
  }
`;

const Title = styled.h2`
  align-self: flex-start;
  font-size: 24px;
  font-weight: 700;
`;

const Avatar = styled.img`
  align-self: center;
  width: 120px;
  height: 120px;
  border-radius: 50%;
`;

const Label = styled.label`
  margin-top: 16px;
  font-size: 18px;
  font-weight: 700;
`;

const Input = styled.input`
  padding: 0 8px;

  font-size: 18px;
  line-height: 2.4;
  color: ${({ theme }) => theme.color.black};

  border: none;
  border-radius: 6px;
  outline: none;
  box-shadow: 0 0 0 1px inset ${({ theme }) => theme.color.black200};

  transition: box-shadow 0.3s ease;

  &:focus {
    box-shadow: 0 0 0 2px inset ${({ theme }) => theme.color.primary};
  }
`;

const BottomError = styled.p`
  font-size: 14px;
  color: ${({ theme }) => theme.color.error};
`;

const WithdrawalButton = styled.button`
  color: ${({ theme }) => theme.color.disabled};
  text-decoration: underline;
`;

const SubmitButton = styled.button`
  cursor: pointer;

  position: absolute;
  bottom: 0;

  align-self: flex-end;

  width: 100%;
  padding: 11px 20px;

  font-size: 18px;
  font-weight: 700;
  line-height: 1.6;

  background-color: ${({ theme }) => theme.color.primary};
  border: none;
  border-radius: 10px;

  &:disabled {
    color: ${({ theme }) => theme.color.disabled};
    background-color: ${({ theme }) => theme.color.disabledBackground};
  }
`;
