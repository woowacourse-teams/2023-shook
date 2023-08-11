import { styled } from 'styled-components';
import { KILLING_PART_INTERVAL } from '../constants/killingPartInterval';
import useVoteInterfaceContext from '../hooks/useVoteInterfaceContext';

const KillingPartToggleGroup = () => {
  const { interval, updateKillingPartInterval } = useVoteInterfaceContext();

  return (
    <ToggleGroup>
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === KILLING_PART_INTERVAL.FIVE}
        data-interval={KILLING_PART_INTERVAL.FIVE}
        $active={interval === KILLING_PART_INTERVAL.FIVE}
        onClick={updateKillingPartInterval}
      >
        {KILLING_PART_INTERVAL.FIVE}초
      </ToggleGroupItem>
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === KILLING_PART_INTERVAL.TEN}
        data-interval={KILLING_PART_INTERVAL.TEN}
        $active={interval === KILLING_PART_INTERVAL.TEN}
        onClick={updateKillingPartInterval}
      >
        {KILLING_PART_INTERVAL.TEN}초
      </ToggleGroupItem>
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === KILLING_PART_INTERVAL.FIFTEEN}
        data-interval={KILLING_PART_INTERVAL.FIFTEEN}
        $active={interval === KILLING_PART_INTERVAL.FIFTEEN}
        onClick={updateKillingPartInterval}
      >
        {KILLING_PART_INTERVAL.FIFTEEN}초
      </ToggleGroupItem>
    </ToggleGroup>
  );
};

export default KillingPartToggleGroup;

const ToggleGroup = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  width: 100%;
  gap: 20px;
`;

const ToggleGroupItem = styled.button<{ $active: boolean }>`
  flex: 1;
  margin: 0;
  padding: 0;
  border: none;
  border-radius: 10px;
  min-width: 50px;
  height: 30px;
  font-weight: ${({ $active }) => ($active ? '700' : '500')};
  color: ${({ $active, theme: { color } }) => ($active ? color.black : color.white)};
  background-color: ${({ $active, theme: { color } }) => ($active ? color.white : color.secondary)};
  cursor: pointer;
`;
