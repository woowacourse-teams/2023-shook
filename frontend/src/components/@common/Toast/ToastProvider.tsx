import { createContext, useCallback, useRef, useState } from 'react';
import Toast from './Toast';
import ToastList from './ToastList';
import type { PropsWithChildren } from 'react';

interface ToastContextProps {
  showToast: (message: string) => void;
}

export const ToastContext = createContext<null | ToastContextProps>(null);

const ToastProvider = ({ children }: PropsWithChildren) => {
  const [isToastShow, setIsToastShow] = useState(false);
  const [message, setMessage] = useState('');
  const toastTimer = useRef<NodeJS.Timeout>();

  const showToast = useCallback((message: string) => {
    setIsToastShow(true);
    setMessage(message);

    if (toastTimer.current) {
      clearTimeout(toastTimer.current);
    }

    const timer = setTimeout(() => {
      hideToast();
    }, 2000);

    toastTimer.current = timer;
  }, []);

  const hideToast = useCallback(() => {
    setIsToastShow(false);
    setMessage('');
  }, []);

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}
      {isToastShow && (
        <ToastList>
          <Toast message={message} />
        </ToastList>
      )}
    </ToastContext.Provider>
  );
};

export default ToastProvider;
