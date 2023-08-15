import { useState } from 'react';

const useKillingPartLikes = (likeStatus: boolean) => {
  const [isLikes, setIsLikes] = useState(likeStatus);

  const toggleLikes = () => {
    setIsLikes((prev) => !prev);
  };

  return { isLikes, toggleLikes };
};

export default useKillingPartLikes;
