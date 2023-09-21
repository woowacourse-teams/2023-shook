import { createContext, useContext } from 'react';
import LoginModal from '@/features/auth/components/LoginModal';
import useModal from '@/shared/components/Modal/hooks/useModal';
import type { PropsWithChildren } from 'react';

interface LoginPopUpContextProps {
  popupLoginModal: () => void;
}

const LoginPopUpContext = createContext<LoginPopUpContextProps | null>(null);

export const useLoginPopup = () => {
  const contextValue = useContext(LoginPopUpContext);

  if (contextValue === null) throw new Error('AuthContext가 null입니다.');

  return contextValue;
};

const LoginPopupProvider = ({ children }: PropsWithChildren) => {
  const { isOpen, closeModal, openModal: popupLoginModal } = useModal(false);

  return (
    <LoginPopUpContext.Provider value={{ popupLoginModal }}>
      {children}
      <LoginModal isOpen={isOpen} closeModal={closeModal} message={'로그인 하시겠습니까?'} />
    </LoginPopUpContext.Provider>
  );
};

export default LoginPopupProvider;
