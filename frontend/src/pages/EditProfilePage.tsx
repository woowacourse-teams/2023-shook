import styled from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import NicknameChangingModal from '@/features/member/components/NicknameChangingModal';
import WithdrawalModal from '@/features/member/components/WithdrawalModal';
import useNickname from '@/features/member/hooks/useNickname';
import useWithdrawal from '@/features/member/hooks/useWithdrawal';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Spacing from '@/shared/components/Spacing';

const EditProfilePage = () => {
  const {
    nicknameEntered,
    nicknameErrorMessage,
    hasError,
    handleChangeNickname,
    submitNicknameChanged,
  } = useNickname();

  const { handleWithdrawal } = useWithdrawal();

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
      {hasError && <BottomError>{nicknameErrorMessage}</BottomError>}
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
