import { useNavigate } from 'react-router-dom';
import styled, { css } from 'styled-components';
import shookshook from '@/assets/icon/shookshook.svg';
import { useAuthContext } from '@/features/auth/components/AuthProvider';
import WITHDRAWAL_MESSAGE from '@/features/member/constants/withdrawalMessage';
import { deleteMember } from '@/features/member/remotes/member';
import { useConfirmContext } from '@/shared/components/ConfirmModal/hooks/useConfirmContext';
import Spacing from '@/shared/components/Spacing';
import ROUTE_PATH from '@/shared/constants/path';
import { useMutation } from '@/shared/hooks/useMutation';

const EditProfilePage = () => {
  const { user, logout } = useAuthContext();
  const { confirmPopup } = useConfirmContext();
  const { mutateData: withdrawMember } = useMutation(deleteMember(user?.memberId));
  const navigate = useNavigate();

  if (!user) {
    navigate(ROUTE_PATH.LOGIN);
    return;
  }

  const handleClickWithdrawal = async () => {
    const isConfirmed = await confirmPopup({
      title: '회원 탈퇴',
      content: <ModalContent>{WITHDRAWAL_MESSAGE}</ModalContent>,
      confirmation: '탈퇴',
      denial: '닫기',
    });

    if (isConfirmed) {
      await withdrawMember();
      logout();
      navigate(ROUTE_PATH.ROOT, { replace: true });
    }
  };

  return (
    <Container>
      <Title>프로필 수정</Title>
      <Spacing direction={'vertical'} size={16} />
      <Avatar src={shookshook} />
      <Label htmlFor="nickname">닉네임</Label>
      <Spacing direction={'vertical'} size={4} />
      <Input id="nickname" value={user.nickname} disabled />
      <Spacing direction={'vertical'} size={16} />
      <Label htmlFor="introduction">소개</Label>
      <Spacing direction={'vertical'} size={4} />
      <TextArea id="introduction" value={''} disabled maxLength={100} />
      <Spacing direction={'vertical'} size={16} />
      <WithdrawalButton onClick={handleClickWithdrawal}>회원 탈퇴</WithdrawalButton>
      <SubmitButton disabled>제출</SubmitButton>
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
  color: ${({ disabled, theme }) => (disabled ? theme.color.black400 : theme.color.black)};
  background-color: ${({ disabled, theme }) =>
    disabled ? theme.color.disabledBackground : theme.color.white};
`;

const Input = styled.input<{ disabled: boolean }>`
  ${disabledStyle};
  padding: 0 8px;
  font-size: 16px;
`;

const TextArea = styled.textarea<{ disabled: boolean }>`
  ${disabledStyle};
  resize: none;
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

const ModalContent = styled.div`
  align-self: start;

  font-size: 16px;
  line-height: 200%;
  color: ${({ theme }) => theme.color.subText};
  white-space: pre-line;
`;
