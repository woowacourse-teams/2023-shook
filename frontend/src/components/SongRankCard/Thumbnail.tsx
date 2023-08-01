import { Wrapper } from './Thumbnail.style';
import type { ImgHTMLAttributes } from 'react';

interface ThumbnailProps extends ImgHTMLAttributes<HTMLImageElement> {}

const Thumbnail = ({ ...props }: ThumbnailProps) => {
  return (
    <Wrapper>
      <img {...props} style={{ borderRadius: '8px' }} />
    </Wrapper>
  );
};

export default Thumbnail;
