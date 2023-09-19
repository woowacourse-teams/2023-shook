import LoginModal from '@/features/auth/components/LoginModal';
import useModal from '@/shared/components/Modal/hooks/useModal';

const LoginPopUp = () => {
  const { isOpen, closeModal } = useModal(true);

  return (
    <LoginModal isOpen={isOpen} closeModal={closeModal} messageList={['로그인 하시겠습니까?']} />
  );
};

export default LoginPopUp;
