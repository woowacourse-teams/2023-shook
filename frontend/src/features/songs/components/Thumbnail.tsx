import { css, styled } from 'styled-components';
import defaultAlbumJacket from '@/assets/icon/album-jacket-default.svg';
import type { ImgHTMLAttributes, SyntheticEvent } from 'react';

interface ThumbnailProps extends ImgHTMLAttributes<HTMLImageElement> {
  size?: Size;
}

const Thumbnail = ({ size = 'lg', ...props }: ThumbnailProps) => {
  const insertDefaultJacket = ({ currentTarget }: SyntheticEvent<HTMLImageElement>) => {
    currentTarget.src = defaultAlbumJacket;
  };

  return (
    <Wrapper $size={size}>
      <img {...props} alt="노래 앨범" aria-hidden loading="lazy" onError={insertDefaultJacket} />
    </Wrapper>
  );
};

export default Thumbnail;

const Wrapper = styled.div<{ $size: Size }>`
  overflow: hidden;
  ${({ $size }) => SIZE_VARIANTS[$size]};
  border-radius: 4px;
`;

const SIZE_VARIANTS = {
  md: css`
    width: 60px;
    height: 60px;
  `,
  lg: css`
    width: 70px;
    height: 70px;
  `,
  xl: css`
    width: 120px;
    height: 120px;
  `,
} as const;

type Size = keyof typeof SIZE_VARIANTS;
