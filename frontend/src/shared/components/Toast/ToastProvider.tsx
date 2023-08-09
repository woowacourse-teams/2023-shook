import { createContext, useCallback, useRef, useState } from 'react';
import { createPortal } from 'react-dom';
import Toast from './Toast';
import ToastList from './ToastList';
import type { PropsWithChildren } from 'react';

interface ToastContextProps {
  showToast: (message: string) => void;
}

export const ToastContext = createContext<ToastContextProps | null>(null);

const ToastProvider = ({ children }: PropsWithChildren) => {
  const [isToastShow, setIsToastShow] = useState(false);
  const [message, setMessage] = useState('');
  const toastTimer = useRef<number | null>(null);

  const showToast = useCallback((message: string) => {
    if (toastTimer.current !== null) return;

    setMessage(message);
    setIsToastShow(true);

    const timer = window.setTimeout(hideToast, 2000);
    toastTimer.current = timer;
  }, []);

  const hideToast = useCallback(() => {
    if (toastTimer.current === null) return;

    setIsToastShow(false);
    setMessage('');

    window.clearTimeout(toastTimer.current);
    toastTimer.current = null;
  }, []);

  return (
    <ToastContext.Provider value={{ showToast }}>
      {children}
      {isToastShow &&
        createPortal(
          <ToastList>
            <Toast message={message} />
          </ToastList>,
          document.body
        )}
    </ToastContext.Provider>
  );
};

export default ToastProvider;
