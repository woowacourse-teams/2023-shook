import { useState } from 'react';
import { ToggleGroupItem, Spacing, ToggleGroup } from './KillingPartToggleGroup.style';
import type { KillingPartInterval } from './KillingPartToggleGroup.type';
import type { MouseEventHandler } from 'react';

const KillingPartToggleGroup = () => {
  const [interval, setInterval] = useState<KillingPartInterval>(10);

  const setKillingPartInterval: MouseEventHandler<HTMLButtonElement> = ({ currentTarget }) => {
    const newInterval = Number(currentTarget.getAttribute('data-interval')!) as KillingPartInterval;

    setInterval(newInterval);
  };

  return (
    <ToggleGroup>
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === 5}
        data-interval={5}
        active={interval === 5}
        onClick={setKillingPartInterval}
      >
        5초
      </ToggleGroupItem>
      <Spacing direction="horizontal" size={15} />
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === 10}
        data-interval={10}
        active={interval === 10}
        onClick={setKillingPartInterval}
      >
        10초
      </ToggleGroupItem>
      <Spacing direction="horizontal" size={15} />
      <ToggleGroupItem
        role="radio"
        type="button"
        aria-checked={interval === 15}
        data-interval={15}
        active={interval === 15}
        onClick={setKillingPartInterval}
      >
        15초
      </ToggleGroupItem>
    </ToggleGroup>
  );
};

export default KillingPartToggleGroup;
