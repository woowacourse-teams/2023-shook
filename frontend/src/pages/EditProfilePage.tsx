import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import googleAuthUrl from '@/features/auth/constants/googleAuthUrl';
import WithdrawalModal from '@/features/profile/components/WithdrawalModal';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';
import fetcher from '@/shared/remotes';

const EditProfilePage = () => {
  const { user, logout } = useAuthContext();
  const { isOpen, openModal, closeModal } = useModal();

  const { mutateData } = useMutation(() => fetcher(`/members/${user?.memberId}`, 'DELETE'));
  const navigate = useNavigate();

  if (!user) {
    window.location.href = googleAuthUrl;
    return;
  }

  const handleWithdrawal = async () => {
    await mutateData();
    logout();
    navigate('/');
  };

  return (
    <Container>
      <Title>프로필 수정</Title>
      <Spacing direction={'vertical'} size={16} />
      <Avatar src={shookshook} />
      <SubTitle>닉네임</SubTitle>
      <Spacing direction={'vertical'} size={4} />
      <Input value={user.nickname} disabled />
      <Spacing direction={'vertical'} size={16} />
      <SubTitle>소개</SubTitle>
      <Spacing direction={'vertical'} size={4} />
      <TextArea value={''} disabled maxLength={100} />
      <Spacing direction={'vertical'} size={16} />
      <WithdrawalButton onClick={openModal}>회원 탈퇴</WithdrawalButton>
      <SubmitButton disabled>제출</SubmitButton>
      <WithdrawalModal isOpen={isOpen} closeModal={closeModal} onWithdraw={handleWithdrawal} />
    </Container>
  );
};

export default EditProfilePage;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  position: relative;
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
  font-size: 28px;
  font-weight: 700;
`;

const Avatar = styled.img`
  width: 120px;
  height: 120px;
  border-radius: 50%;
  align-self: center;
`;

const SubTitle = styled.h3`
  font-weight: 700;
  font-size: 20px;
`;

const disabledStyle = css<{ disabled: boolean }>`
  color: ${({ disabled, theme }) => (disabled ? theme.color.black400 : theme.color.black)};
  background-color: ${({ disabled, theme }) =>
    disabled ? theme.color.disabledBackground : theme.color.white};
`;

const Input = styled.input<{ disabled: boolean }>`
  ${disabledStyle};
  font-size: 20px;
  padding: 0 8px;
`;

const TextArea = styled.textarea<{ disabled: boolean }>`
  ${disabledStyle}
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
