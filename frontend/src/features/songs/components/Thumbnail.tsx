import { styled } from 'styled-components';
import defaultAlbumJacket from '@/assets/icon/album-jacket-default.svg';
import type { ImgHTMLAttributes, SyntheticEvent } from 'react';

interface ThumbnailProps extends ImgHTMLAttributes<HTMLImageElement> {}

const Thumbnail = ({ ...props }: ThumbnailProps) => {
  const insertDefaultJacket = ({ currentTarget }: SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = defaultAlbumJacket;
  };

  return (
    <Wrapper>
      <img {...props} alt="노래 앨범" aria-hidden loading="lazy" onError={insertDefaultJacket} />
    </Wrapper>
  );
};

export default Thumbnail;

const Wrapper = styled.div`
  width: 70px;
  height: 70px;

  border-radius: 8px;
  overflow: hidden;
`;
