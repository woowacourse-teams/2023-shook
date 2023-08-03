import { styled } from 'styled-components';

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 16px;
`;

export const RegisterTitle = styled.p`
  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;

export const Register = styled.button<{ disabled: boolean }>`
  width: 100%;
  height: 36px;
  border: none;
  border-radius: 10px;
  background-color: ${({ disabled, theme: { color } }) => {
    return disabled ? color.disabledBackground : color.primary;
  }};

  color: ${({ disabled, theme: { color } }) => {
    return disabled ? color.disabled : color.white;
  }};

  cursor: pointer;
`;

export const ModalTitle = styled.h3``;

export const ModalContent = styled.div`
  font-size: 16px;
  color: #b5b3bc;
  white-space: pre-line;
  padding: 16px 0;
  text-align: center;
`;

export const Message = styled.div``;

export const Button = styled.button`
  height: 36px;
  border: none;
  border-radius: 10px;
  color: ${({ theme: { color } }) => color.white};
  cursor: pointer;
`;

export const Confirm = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.secondary};
`;

export const Share = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.primary};
`;
