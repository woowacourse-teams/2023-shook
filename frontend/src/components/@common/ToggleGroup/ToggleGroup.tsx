import { useState, useContext } from 'react';
import { ToggleContext } from './hooks/useToggleContext';
import { Container, Item } from './ToggleGroup.style';
import type { PropsWithChildren } from 'react';

interface ToggleGroupProps extends PropsWithChildren {
  defaultValue?: string;
  onChangeValue: (value: string) => void;
}

interface ToggleButtonProps extends PropsWithChildren {
  value: string;
}

const ToggleGroup = ({ defaultValue, onChangeValue, children }: ToggleGroupProps) => {
  const [selected, setSelected] = useState(defaultValue);

  return (
    <ToggleContext.Provider value={{ selected, setSelected, onChangeValue }}>
      <Container>{children}</Container>
    </ToggleContext.Provider>
  );
};

const ToggleButton = ({ value, children }: ToggleButtonProps) => {
  const toggleContext = useContext(ToggleContext);

  if (!toggleContext) {
    throw new Error('toggle group 사용 시에 반드시 Context API를 공유해야합니다.');
  }

  const { selected, setSelected, onChangeValue } = toggleContext;

  const isActive = selected === value;

  const handleClickButton = () => {
    onChangeValue(value);
    setSelected(value);
  };

  return (
    <Item $active={isActive} onClick={handleClickButton}>
      {children}
    </Item>
  );
};

ToggleGroup.button = ToggleButton;

export default ToggleGroup;
