import { styled } from 'styled-components';

export const SwitchWrapper = styled.label`
  position: relative;
  display: inline-block;
  width: 40px;
  height: 20px;
`;

export const SwitchInput = styled.input`
  opacity: 0;
  width: 0;
  height: 0;

  &:checked + span {
    background-color: ${({ theme }) => theme.color.primary};
  }

  &:checked + span:before {
    transform: translateX(18px);
  }
`;

export const SwitchSlider = styled.span`
  position: absolute;
  cursor: pointer;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  border-radius: 32px;
  background-color: ${({ theme: { color } }) => color.disabledBackground};
  transition: 0.4s;

  &:before {
    box-shadow: 1px 1px ${({ theme: { color } }) => color.black};
    position: absolute;
    border-radius: 50%;
    content: '';
    height: 16px;
    width: 16px;
    left: 3px;
    bottom: 2px;
    background-color: white;
    transition: 0.4s;
  }
`;