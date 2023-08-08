import { useContext } from 'react';
import { ToastContext } from '../ToastProvider';

const useToastContext = () => {
  const value = useContext(ToastContext);
  if (!value) throw new Error('토스트 컨텍스트에 제공되는 값이 없습니다.');

  return value;
};

export default useToastContext;
