import useToastContext from '@/components/@common/Toast/hooks/useToastContext';

const useCopyClipBoard = () => {
  const { showToast } = useToastContext();
  const copyClipboard = async (textToCopy: string, message?: string) => {
    if (!textToCopy) return;

    try {
      await navigator.clipboard.writeText(textToCopy);
    } catch {
      const el = document.createElement('textarea');
      el.value = textToCopy;

      document.body.appendChild(el);
      el.select();
      document.execCommand('copy');
      document.body.removeChild(el);
    }

    if (message) showToast(message);
  };

  return { copyClipboard };
};

export default useCopyClipBoard;
