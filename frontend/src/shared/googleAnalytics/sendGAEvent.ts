interface GAProps {
  action: string;
  category?: string;
  memberId?: number;
}

export const sendGAEvent = ({ action, category, memberId }: GAProps) => {
  if ('gtag' in window) {
    window?.gtag('event', action, {
      event_category: category,
      member_id: memberId,
    });
  }
};

export default sendGAEvent;
