import { css, styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';
import Flex from '@/shared/components/Flex/Flex';

const VideoIntervalStepper = () => {
  const { interval, plusPartInterval, minusPartInterval } = useVoteInterfaceContext();

  return (
    <Flex $gap={20}>
      <ControlButton onClick={minusPartInterval}>-</ControlButton>
      <CountText>{`${interval} 초`}</CountText>
      <ControlButton onClick={plusPartInterval}>+</ControlButton>
    </Flex>
  );
};

export default VideoIntervalStepper;

const StepperElementStyle = css`
  flex: 1;

  min-width: 50px;
  margin: 0;
  padding: 4px 11px;

  font-weight: 700;
  line-height: 1.8;

  text-align: center;

  border: none;
  border-radius: 10px;
`;

const ControlButton = styled.button`
  ${StepperElementStyle};

  color: ${({ theme: { color } }) => color.white};
  background-color: ${({ theme: { color } }) => color.secondary};

  &:active {
    background-color: ${({ theme: { color } }) => color.disabled};
    transition: box-shadow 0.2s ease;
  }
`;

const CountText = styled.p`
  ${StepperElementStyle};

  color: ${({ theme: { color } }) => color.black};
  background-color: ${({ theme: { color } }) => color.white};

  &:active {
    box-shadow: 0 0 0 1px inset pink;
    transition: box-shadow 0.1s ease;
  }
`;
