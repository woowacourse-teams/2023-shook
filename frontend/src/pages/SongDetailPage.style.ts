import { styled } from 'styled-components';

export const ModalTitle = styled.h3``;

export const ModalContent = styled.p`
  font-size: 16px;
  color: #b5b3bc;
  white-space: pre-line;
  padding: 16px 0;
`;

export const Button = styled.button`
  height: 36px;
  border: none;
  border-radius: 10px;

  color: #ffffff;

  cursor: pointer;
`;

export const Register = styled(Button)<{ disabled: boolean }>`
  background-color: ${({ disabled, theme: { color } }) => {
    return disabled ? color.disabledBackground : color.primary;
  }};

  color: ${({ disabled, theme: { color } }) => {
    return disabled ? color.disabled : color.white;
  }};

  width: 100%;
`;

export const Confirm = styled(Button)`
  flex: 1;

  background-color: #2d2c35;
`;

export const Share = styled(Button)`
  flex: 1;

  background-color: #de2e5f;
`;

export const Flex = styled.div`
  display: flex;
  width: 100%;
`;

export const Spacing = styled.div<{ direction: 'horizontal' | 'vertical'; size: number }>`
  flex: none;
  width: ${({ direction, size }) => (direction === 'horizontal' ? `${size}px` : undefined)};
  height: ${({ direction, size }) => (direction === 'vertical' ? `${size}px` : undefined)};
`;

export const SongTitle = styled.p`
  text-align: start;
  font-size: 18px;
  font-weight: 800;
  color: ${({ theme: { color } }) => color.white};
`;

export const Singer = styled.p`
  text-align: start;
  font-size: 14px;
  font-weight: 600;
  color: ${({ theme: { color } }) => color.subText};
`;

export const Container = styled.section`
  display: flex;
  width: 100%;
  flex-direction: column;
  justify-items: start;
`;

export const RegisterTitle = styled.p`
  color: ${({ theme: { color } }) => color.white};
  margin-top: 16px;
`;
