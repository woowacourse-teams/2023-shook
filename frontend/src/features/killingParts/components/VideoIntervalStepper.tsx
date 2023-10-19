import { css, styled } from 'styled-components';
import useCollectingPartContext from '@/features/killingParts/hooks/useCollectingPartContext';
import Flex from '@/shared/components/Flex/Flex';

const VideoIntervalStepper = () => {
  const { interval, increasePartInterval, decreasePartInterval } = useCollectingPartContext();

  return (
    <Flex $gap={20}>
      <ControlButton onClick={decreasePartInterval}>-</ControlButton>
      <CountText>{`${interval} 초`}</CountText>
      <ControlButton onClick={increasePartInterval}>+</ControlButton>
    </Flex>
  );
};

export default VideoIntervalStepper;

const StepperElementStyle = css`
  flex: 1;

  min-width: 50px;
  height: 36px;
  margin: 0;

  font-weight: 700;
  text-align: center;

  border: none;
  border-radius: 10px;
`;

const ControlButton = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;

  font-size: 24px;
  color: ${({ theme: { color } }) => color.white};

  background-color: ${({ theme: { color } }) => color.secondary};

  &:active {
    background-color: ${({ theme: { color } }) => color.disabled};
    transition: box-shadow 0.2s ease;
  }

  ${StepperElementStyle}
`;

const CountText = styled.p`
  display: flex;
  align-items: center;
  justify-content: center;

  color: ${({ theme: { color } }) => color.black};

  background-color: ${({ theme: { color } }) => color.white};

  &:active {
    box-shadow: 0 0 0 1px inset ${({ theme: { color } }) => color.magenta300};
    transition: box-shadow 0.1s ease;
  }

  ${StepperElementStyle}
`;
