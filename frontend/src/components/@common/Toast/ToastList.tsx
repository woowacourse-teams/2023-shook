import { styled } from 'styled-components';
import type { PropsWithChildren } from 'react';

const ToastList = ({ children }: PropsWithChildren) => {
  return <Position>{children}</Position>;
};

export default ToastList;

const Position = styled.div`
  position: fixed;
  bottom: 10%;
  left: 50%;
  transform: translate(-50%, 0);
`;
