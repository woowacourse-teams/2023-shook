import { useState } from 'react';
import type { KillingPartInterval } from '../types/KillingPartToggleGroup.type';

const useKillingPartInterval = (initialInterval: KillingPartInterval = 10) => {
  const [interval, setInterval] = useState(initialInterval);
  const setKillingPartInterval: React.MouseEventHandler<HTMLButtonElement> = ({
    currentTarget,
  }) => {
    const newInterval = Number(currentTarget.getAttribute('data-interval')!) as KillingPartInterval;
    setInterval(newInterval);
  };

  return {
    interval,
    setKillingPartInterval,
  };
};

export default useKillingPartInterval;
