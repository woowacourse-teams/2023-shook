import { GA_CATEGORIES, GA_MEMBER } from '@/shared/constants/GAEventName';

interface GAProps {
  action: string;
  category?: string;
  memberId?: number;
}

export const sendGAEvent = ({
  action,
  category = GA_CATEGORIES.NOT_COLLECTING,
  memberId = GA_MEMBER.NOT_COLLECTING,
}: GAProps) => {
  if ('gtag' in window) {
    window?.gtag('event', action, {
      event_category: category,
      member_id: memberId,
    });
  }
};

export default sendGAEvent;
