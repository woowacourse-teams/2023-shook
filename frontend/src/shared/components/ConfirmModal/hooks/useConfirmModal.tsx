import { useOverlay } from '@/shared/hooks/useOverlay';
import ConfirmModal from '../ConfirmModal';
import type { ReactNode } from 'react';

interface OpenConfirmModalProps {
  /**
   * 제목
   */
  title: string;
  /**
   * 내용
   */
  content: ReactNode;
  /**
   * 취소 버튼 이름
   */
  denial?: string;
  /**
   * 확인 버튼 이름
   */
  confirmation?: string;
}

export const useConfirmModal = () => {
  const overlay = useOverlay();

  const openConfirmModal = ({
    title,
    content,
    denial = '닫기',
    confirmation = '확인',
  }: OpenConfirmModalProps) => {
    return new Promise<boolean>((resolve) =>
      overlay.open(({ isOpen, close }) => (
        <ConfirmModal
          isOpen={isOpen}
          closeModal={() => {
            resolve(false);
            close();
          }}
          title={title}
          content={content}
          denial={denial}
          confirmation={confirmation}
          onDeny={() => {
            resolve(false);
            close();
          }}
          onConfirm={() => {
            resolve(true);
            close();
          }}
        />
      ))
    );
  };

  return { openConfirmModal };
};
