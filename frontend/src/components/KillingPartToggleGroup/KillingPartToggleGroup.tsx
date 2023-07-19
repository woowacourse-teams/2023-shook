import { KILLING_PART_INTERVAL } from './constants';
import { ToggleGroupItem, Spacing, ToggleGroup } from './KillingPartToggleGroup.style';
import type { KillingPartInterval } from './KillingPartToggleGroup.type';
import type { MouseEventHandler } from 'react';

interface KillingPartToggleGroupProps {
  interval: KillingPartInterval;
  setKillingPartInterval: MouseEventHandler<HTMLButtonElement>;
}

const KillingPartToggleGroup = ({
  interval,
  setKillingPartInterval,
}: KillingPartToggleGroupProps) => {
  return (
    <ToggleGroup>
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === KILLING_PART_INTERVAL.FIVE}
        data-interval={KILLING_PART_INTERVAL.FIVE}
        active={interval === KILLING_PART_INTERVAL.FIVE}
        onClick={setKillingPartInterval}
      >
        {KILLING_PART_INTERVAL.FIVE}초
      </ToggleGroupItem>
      <Spacing direction="horizontal" size={15} />
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === KILLING_PART_INTERVAL.TEN}
        data-interval={KILLING_PART_INTERVAL.TEN}
        active={interval === KILLING_PART_INTERVAL.TEN}
        onClick={setKillingPartInterval}
      >
        {KILLING_PART_INTERVAL.TEN}초
      </ToggleGroupItem>
      <Spacing direction="horizontal" size={15} />
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === KILLING_PART_INTERVAL.FIFTEEN}
        data-interval={KILLING_PART_INTERVAL.FIFTEEN}
        active={interval === KILLING_PART_INTERVAL.FIFTEEN}
        onClick={setKillingPartInterval}
      >
        {KILLING_PART_INTERVAL.FIFTEEN}초
      </ToggleGroupItem>
    </ToggleGroup>
  );
};

export default KillingPartToggleGroup;
