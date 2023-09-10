import styled from 'styled-components';
import useModal from '@/shared/components/Modal/hooks/useModal';
import Modal from '@/shared/components/Modal/Modal';
import Spacing from '@/shared/components/Spacing';

const EditProfilePage = () => {
  // const { user } = useAuthContext();

  // if (!user) {
  //   window.location.href = googleAuthUrl;
  // }

  const { isOpen, openModal, closeModal } = useModal();
  const nickname = 'ukko';

  return (
    <Container>
      <Title>프로필 수정</Title>
      <Spacing direction={'vertical'} size={16} />
      <SubTitle>닉네임</SubTitle>
      <Spacing direction={'vertical'} size={4} />
      <Input value={nickname} disabled />
      <Spacing direction={'vertical'} size={16} />
      <SubTitle>소개</SubTitle>
      <Spacing direction={'vertical'} size={4} />
      <TextArea value={''} disabled maxLength={100} />
      <Spacing direction={'vertical'} size={16} />
      <WithdrawalButton onClick={openModal}>회원 탈퇴</WithdrawalButton>
      <SubmitButton disabled>제출</SubmitButton>
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <>
          <ModalContent>{'정말 회원 탈퇴하겠습니까?'}</ModalContent>
          <Spacing direction={'vertical'} size={16} />
          <ButtonContainer>
            <ConfirmButton type="button" onClick={closeModal}>
              회원 탈퇴
            </ConfirmButton>
            <CancelButton onClick={closeModal} type="button">
              닫기
            </CancelButton>
          </ButtonContainer>
        </>
      </Modal>
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

const SubTitle = styled.h3`
  font-size: 20px;
`;

const Input = styled.input`
  color: ${({ theme }) => theme.color.black};
`;

const TextArea = styled.textarea`
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
  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ disabled, theme: { color } }) =>
    disabled ? color.disabledBackground : color.primary};
  border: none;
  border-radius: 10px;
`;

const ModalContent = styled.div`
  margin: 16px 0;

  font-size: 16px;
  color: #b5b3bc;
  text-align: center;
  white-space: pre-line;
`;

const Button = styled.button`
  cursor: pointer;

  height: 36px;

  color: ${({ theme: { color } }) => color.white};

  border: none;
  border-radius: 10px;
`;

const ConfirmButton = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.secondary};
`;

const CancelButton = styled(Button)`
  flex: 1.5;
  background-color: ${({ theme: { color } }) => color.primary};
`;

const ButtonContainer = styled.div`
  display: flex;
  gap: 16px;
  width: 100%;
`;
