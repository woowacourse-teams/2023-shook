import { styled } from 'styled-components';
import successIcon from '@/assets/icon/toast-success.svg';
import { fadeInBottomToUp } from './keyframes/fadeInBottomToUp';

interface ToastProps {
  message: string;
}

const Toast = ({ message }: ToastProps) => {
  return (
    <Container>
      <StatusIcon src={successIcon} alt="사용자 작업 성공" aria-hidden />
      <Message role="alert">{message}</Message>
    </Container>
  );
};

export default Toast;

const Container = styled.div`
  position: relative;

  display: flex;
  gap: 4px;
  align-items: center;

  width: 300px;
  height: 50px;
  padding: 14px;

  background: #323232;
  border-radius: 60px;

  animation: ${fadeInBottomToUp} 1s ease;
`;

const StatusIcon = styled.img`
  width: 22px;
  height: 22px;
`;

const Message = styled.span`
  font-size: 14px;
  color: #ffffff;
`;
