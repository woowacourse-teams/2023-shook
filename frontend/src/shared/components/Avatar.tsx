import { styled } from 'styled-components';
import type { ImgHTMLAttributes } from 'react';

interface AvatarProps extends ImgHTMLAttributes<HTMLImageElement> {}

const Avatar = ({ src, alt = '', ...props }: AvatarProps) => {
  return <Img src={src} alt={alt} {...props} />;
};

export default Avatar;

const Img = styled.img`
  width: 40px;
  height: 40px;

  border-radius: 50%;
`;
