import { useContext } from 'react';
import { ConfirmContext } from '../ConfirmModalProvider';

export const useConfirm = () => {
  const contextValue = useContext(ConfirmContext);
  if (!contextValue) {
    throw new Error('ConfirmContext Provider 내부에서 사용 가능합니다.');
  }

  return contextValue;
};
