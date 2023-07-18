import { useCallback, useState } from 'react';

const useModal = (defaultOpen = false) => {
  const [isOpen, setOpen] = useState(defaultOpen);

  const openModal = useCallback(() => setOpen(true), []);
  const closeModal = useCallback(() => setOpen(false), []);

  return {
    isOpen,
    openModal,
    closeModal,
  };
};

export default useModal;
