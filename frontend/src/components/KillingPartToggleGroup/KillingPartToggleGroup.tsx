import { useState } from 'react';
import { KILLING_PART_INTERVAL } from './constants';
import { ToggleGroupItem, Spacing, ToggleGroup } from './KillingPartToggleGroup.style';
import type { KillingPartInterval } from './KillingPartToggleGroup.type';
import type { MouseEventHandler } from 'react';

const KillingPartToggleGroup = () => {
  const [interval, setInterval] = useState<KillingPartInterval>(KILLING_PART_INTERVAL.TEN);

  const setKillingPartInterval: MouseEventHandler<HTMLButtonElement> = ({ currentTarget }) => {
    const newInterval = Number(currentTarget.getAttribute('data-interval')!) as KillingPartInterval;

    setInterval(newInterval);
  };

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
        5초
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
        10초
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
        15초
      </ToggleGroupItem>
    </ToggleGroup>
  );
};

export default KillingPartToggleGroup;
