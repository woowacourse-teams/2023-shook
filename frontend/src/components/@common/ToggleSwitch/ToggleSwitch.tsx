import { useState } from 'react';
import { SwitchInput, SwitchSlider, SwitchWrapper } from './ToggleSwitch.style';

interface ToggleSwitchProps {
  off: () => void;
  on: () => void;
  defaultToggle?: boolean;
}

const ToggleSwitch = ({ on, off, defaultToggle = false }: ToggleSwitchProps) => {
  const [toggle, setToggle] = useState(defaultToggle);

  const onToggle = () => {
    if (toggle) {
      off();
    } else {
      on();
    }

    setToggle(!toggle);
  };

  return (
    <SwitchWrapper>
      <SwitchInput type="checkbox" defaultChecked={toggle} onChange={onToggle} />
      <SwitchSlider />
    </SwitchWrapper>
  );
};

export default ToggleSwitch;
