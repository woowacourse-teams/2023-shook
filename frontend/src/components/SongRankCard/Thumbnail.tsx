import defaultAlbumJacket from '@/assets/icon/album-jacket-default.svg';
import { Wrapper } from './Thumbnail.style';
import type { ImgHTMLAttributes, SyntheticEvent } from 'react';

interface ThumbnailProps extends ImgHTMLAttributes<HTMLImageElement> {}

const Thumbnail = ({ ...props }: ThumbnailProps) => {
  const insertDefaultJacket = ({ currentTarget }: SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = defaultAlbumJacket;
  };

  return (
    <Wrapper>
      <img
        {...props}
        style={{ borderRadius: '8px' }}
        loading="lazy"
        onError={insertDefaultJacket}
      />
    </Wrapper>
  );
};

export default Thumbnail;
