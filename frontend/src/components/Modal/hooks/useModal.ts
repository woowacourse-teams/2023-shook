import { useCallback, useState } from 'react';

interface UseModalProps {
  defaultOpen: boolean;
}

const useModal = ({ defaultOpen = false }: UseModalProps) => {
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
