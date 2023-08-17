import { useRef, useState } from 'react';
import emptyHeartIcon from '@/assets/icon/empty-heart.svg';
import fillHeartIcon from '@/assets/icon/fill-heart.svg';
import { useMutation } from '@/shared/hooks/useMutation';
import { putKillingPartLikes } from '../remotes/likes';

interface UseKillingPartLikesProps {
  likeCount: number;
  likeStatus: boolean;
  songId: number;
  partId: number;
}

const useKillingPartLikes = ({
  likeCount,
  likeStatus,
  partId,
  songId,
}: UseKillingPartLikesProps) => {
  const [isLikes, setIsLikes] = useState(likeStatus);
  const LikesTimeOutRef = useRef<number | null>(null);
  const { mutateData: mutateKillingPartLikes } = useMutation(putKillingPartLikes);
  const calculatedLikeCount = isLikes ? likeCount + 1 : likeCount;
  const heartIcon = isLikes ? fillHeartIcon : emptyHeartIcon;

  const toggleKillingPartLikes = () => {
    setIsLikes((prev) => !prev);

    if (LikesTimeOutRef.current) {
      window.clearTimeout(LikesTimeOutRef.current);
    }

    LikesTimeOutRef.current = window.setTimeout(() => {
      mutateKillingPartLikes(songId, partId, !isLikes);
    }, 500);
  };

  return { calculatedLikeCount, heartIcon, toggleKillingPartLikes };
};

export default useKillingPartLikes;
