import { useOverlay } from '@/shared/hooks/useOverlay';
import LoginModal from '../components/LoginModal';

export const useLoginModal = () => {
  const overlay = useOverlay();

  const openLoginModal = (errorCode: number) => {
    overlay.open(({ isOpen, close }) => (
      <LoginModal
        isOpen={isOpen}
        closeModal={close}
        message={
          [1000, 1003, 1004].includes(errorCode)
            ? '로그인 정보가 부정확하여 재로그인이 필요합니다.\n다시 로그인하시겠습니까?'
            : [1011, 1012, 1007].includes(errorCode)
            ? '로그인 정보가 만료되었습니다.\n다시 로그인하시겠습니까?'
            : '로그인 하시겠습니까?'
        }
      />
    ));
  };

  return { openLoginModal };
};
