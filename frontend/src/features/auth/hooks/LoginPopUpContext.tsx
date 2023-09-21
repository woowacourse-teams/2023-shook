import { createContext, useContext, useState } from 'react';
import LoginModal from '@/features/auth/components/LoginModal';
import useModal from '@/shared/components/Modal/hooks/useModal';
import type { PropsWithChildren } from 'react';

interface LoginPopUpContextProps {
  popupLoginModal: (errorCode: number) => void;
}

const LoginPopUpContext = createContext<LoginPopUpContextProps | null>(null);

export const useLoginPopup = () => {
  const contextValue = useContext(LoginPopUpContext);

  if (contextValue === null) throw new Error('AuthContext가 null입니다.');

  return contextValue;
};

const LoginPopupProvider = ({ children }: PropsWithChildren) => {
  const { isOpen, closeModal, openModal } = useModal(false);
  const [message, setMessage] = useState('로그인 하시겠습니까?');

  const popupLoginModal = (errorCode: number) => {
    // accessToken 관련 에러 코드
    if ([1000, 1003, 1004].includes(errorCode)) {
      setMessage('로그인 정보가 부정확하여 재로그인이 필요합니다.\n다시 로그인하시겠습니까?');
    }
    // refreshToken 관련 에러 코드
    if ([1011, 1012, 1007].includes(errorCode)) {
      setMessage('로그인 정보가 만료되었습니다.\n다시 로그인하시겠습니까?');
    }

    setMessage('로그인 하시겠습니까?');
    openModal();
  };

  return (
    <LoginPopUpContext.Provider value={{ popupLoginModal }}>
      {children}
      <LoginModal isOpen={isOpen} closeModal={closeModal} message={message} />
    </LoginPopUpContext.Provider>
  );
};

export default LoginPopupProvider;
