import successIcon from '@/assets/icon/toast-success.svg';
import { Container, Message, StatusIcon } from './Toast.style';

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
