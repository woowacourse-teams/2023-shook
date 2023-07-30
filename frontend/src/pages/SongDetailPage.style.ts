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
  color: ${({ theme: { color } }) => color.white};
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
  background-color: ${({ theme: { color } }) => color.secondary};
`;

export const Share = styled(Button)`
  flex: 1;
  background-color: ${({ theme: { color } }) => color.primary};
`;

export const ButtonContainer = styled.div`
  display: flex;
  width: 100%;
`;

export const Spacing = styled.div<{ direction: 'horizontal' | 'vertical'; size: number }>`
  flex: none;
  width: ${({ direction, size }) => (direction === 'horizontal' ? `${size}px` : undefined)};
  height: ${({ direction, size }) => (direction === 'vertical' ? `${size}px` : undefined)};
`;

export const Container = styled.section`
  display: flex;
  width: 100%;
  flex-direction: column;
`;

export const SongInfoContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 12px;
`;

export const Jacket = styled.img`
  width: 60px;
  height: 60px;

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    width: 50px;
    height: 50px;
  }
`;

export const Info = styled.div``;

export const SongTitle = styled.p`
  font-size: 24px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 20px;
  }
`;

export const Singer = styled.p`
  font-size: 18px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.subText};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 16px;
  }
`;

export const RegisterTitle = styled.p`
  font-size: 22px;
  font-weight: 700;
  color: ${({ theme: { color } }) => color.white};

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    font-size: 18px;
  }
`;
