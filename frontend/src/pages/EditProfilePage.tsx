import { useMemo, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
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
  const { user, logout, login } = useAuthContext();
  const [nicknameEntered, setNicknameEntered] = useState(user?.nickname);
  const [errorMessage, setErrorMessage] = useState('');
  const navigate = useNavigate();
  // modal hooks
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

  // 회원탈퇴 API
  const { mutateData: withdrawMember } = useMutation(deleteMember(user?.memberId));

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

  const handleWithdrawal = async () => {
    await withdrawMember();
    logout();
    navigate(ROUTE_PATH.ROOT);
  };

  const handleChangeNickname: React.ChangeEventHandler<HTMLInputElement> = (event) => {
    setNicknameEntered(event.currentTarget.value);
  };

  const submitNicknameChanged = async () => {
    if (nicknameEntered === user?.nickname) {
      setErrorMessage('이전과 다른 닉네임을 사용해주세요.');
      closeNicknameModal();
      return;
    }

    const accessToken = (await changeNickname())?.accessToken;
    if (accessToken) {
      login(accessToken);
      navigate('/my-page');
    }
  };

  return (
    <Container>
      <Title>프로필 수정</Title>
      <Spacing direction={'vertical'} size={16} />
      <Avatar src={shookshook} />
      <Label htmlFor="nickname">닉네임</Label>
      <Spacing direction={'vertical'} size={4} />
      <Input id="nickname" value={nicknameEntered} onChange={handleChangeNickname} />
      <Spacing direction={'vertical'} size={8} />
      <p>{errorMessage}</p>
      <Spacing direction={'vertical'} size={16} />
      <WithdrawalButton onClick={openWithdrawalModal}>회원 탈퇴</WithdrawalButton>
      <SubmitButton onClick={openNicknameModal} disabled={false}>
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
  height: calc(100vh - ${({ theme: { headerHeight } }) => headerHeight.desktop});
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
  font-size: 16px;
  font-weight: 700;
`;

const disabledStyle = css<{ disabled: boolean }>`
  color: ${({ disabled, theme }) => (disabled ? theme.color.black400 : theme.color.white)};
  background-color: ${({ disabled, theme }) =>
    disabled ? theme.color.disabledBackground : theme.color.primary};
`;

const Input = styled.input`
  font-size: 16px;
  padding: 0 8px;
  color: ${({ theme }) => theme.color.black};
`;

const WithdrawalButton = styled.button`
  color: ${({ theme }) => theme.color.disabled};
  text-decoration: underline;
`;

const SubmitButton = styled.button<{ disabled: boolean }>`
  cursor: ${({ disabled }) => (disabled ? 'not-allowed' : 'pointer')};

  position: absolute;
  bottom: 0;

  align-self: flex-end;

  width: 100%;
  height: 36px;

  font-weight: 700;

  ${disabledStyle};
  border: none;
  border-radius: 10px;
`;
