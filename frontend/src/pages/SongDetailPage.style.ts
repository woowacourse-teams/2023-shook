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
  width: ${(props) => (props.direction === 'horizontal' ? `${props.size}px` : undefined)};
  height: ${(props) => (props.direction === 'vertical' ? `${props.size}px` : undefined)};
`;

export const SongTitle = styled.p`
  font-size: 18px;
  font-weight: 800;
  color: ${({ theme: { color } }) => color.white};
`;

export const Singer = styled.p`
  font-size: 14px;
  font-weight: 600;
  color: ${({ theme: { color } }) => color.subText};
`;
