import { useRef, useState } from 'react';

const useToast = () => {
  const [isToastShow, setIsToastShow] = useState(false);
  const [message, setMessage] = useState('');
  const toastTimer = useRef<NodeJS.Timeout>();

  const showToast = (message: string) => {
    setIsToastShow(true);
    setMessage(message);

    if (toastTimer.current) {
      clearTimeout(toastTimer.current);
    }

    const timer = setTimeout(() => {
      hideToast();
    }, 2000);

    toastTimer.current = timer;
  };

  const hideToast = () => {
    setIsToastShow(false);
    setMessage('');
  };

  return { isToastShow, message, showToast, hideToast };
};

export default useToast;
