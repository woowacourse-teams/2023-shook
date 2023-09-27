import { GA_MEMBER } from '@/shared/constants/GAEventName';

interface GAProps {
  action: string;
  category: string;
  memberId?: number;
}

export const sendGAEvent = ({ action, category, memberId = GA_MEMBER.NOT_LOGGED_IN }: GAProps) => {
  if ('gtag' in window) {
    window?.gtag('event', action, {
      event_category: category,
      member_id: memberId ? memberId : GA_MEMBER.NOT_LOGGED_IN,
    });
  }
};

export default sendGAEvent;
