const copyClipboard = async (textToCopy: string) => {
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
};

export default copyClipboard;
