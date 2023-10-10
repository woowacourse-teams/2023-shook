import { styled } from 'styled-components';
import useVoteInterfaceContext from '@/features/songs/hooks/useVoteInterfaceContext';

const PartIntervalController = () => {
  const { interval, plusPartInterval, minusPartInterval } = useVoteInterfaceContext();

  return (
    <Container>
      <ControlButton onClick={minusPartInterval}>-</ControlButton>
      <IntervalItem>{`${interval}초`}</IntervalItem>
      <ControlButton onClick={plusPartInterval}>+</ControlButton>
    </Container>
  );
};

export default PartIntervalController;

const Container = styled.div`
  display: flex;
  flex-direction: row;
  gap: 20px;
  justify-content: center;

  width: 100%;
`;

const ControlButton = styled.button`
  flex: 1;

  min-width: 50px;

  margin: 0;
  padding: 0;

  font-weight: '500';
  color: ${({ theme: { color } }) => color.white};
  text-align: center;
  line-height: 1.8;

  background-color: ${({ theme: { color } }) => color.secondary};
  border: none;
  border-radius: 10px;
`;

const IntervalItem = styled.p`
  flex: 1;

  min-width: 50px;

  margin: 0;

  text-align: center;
  line-height: 1.8;

  font-weight: '700';
  color: ${({ theme: { color } }) => color.black};

  background-color: ${({ theme: { color } }) => color.white};
  border: none;
  border-radius: 10px;
`;
