interface GAProps {
  action: string;
  category?: string;
  label?: string;
  value?: string;
}

const sendGAEvent = ({ action, category = '', label = '', value = '' }: GAProps) => {
  if ('gtag' in window) {
    window?.gtag('event', action, {
      event_category: category,
      event_label: label,
      value: value,
    });
  }
};

export default sendGAEvent;
