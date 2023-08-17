import { Link } from 'react-router-dom';
import ROUTE_PATH from '@/shared/constants/path';
import type { ImgHTMLAttributes } from 'react';

interface LoginLinkProps extends ImgHTMLAttributes<HTMLAnchorElement> {}

const LoginLink = ({ children, ...props }: LoginLinkProps) => {
  return (
    <Link
      {...props}
      to={`https://accounts.google.com/o/oauth2/v2/auth?scope=email&response_type=code&redirect_uri=http://localhost:8080${ROUTE_PATH.LOGIN_REDIRECT}&client_id=1008951336382-8o2n6n9u8jbj3sb6fe5jdeha9b6alnqa.apps.googleusercontent.com`}
    >
      {children}
    </Link>
  );
};

export default LoginLink;
