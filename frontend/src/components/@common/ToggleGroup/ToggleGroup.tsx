import { useState, useContext } from 'react';
import { ToggleContext } from './hooks/useToggleContext';
import { Container, Item } from './ToggleGroup.style';
import type { PropsWithChildren } from 'react';

interface ToggleGroupProps extends PropsWithChildren {
  defaultIndex?: number;
  onChangeButton: (index: number) => void;
}

interface ToggleButtonProps extends PropsWithChildren {
  index: number;
}

const ToggleGroup = ({ defaultIndex, onChangeButton, children }: ToggleGroupProps) => {
  const [selected, setSelected] = useState(defaultIndex);

  return (
    <ToggleContext.Provider value={{ selected, setSelected, onChangeButton }}>
      <Container>{children}</Container>
    </ToggleContext.Provider>
  );
};

const ToggleButton = ({ index, children }: ToggleButtonProps) => {
  const toggleContext = useContext(ToggleContext);

  if (!toggleContext) {
    throw new Error('toggle group 사용 시에 반드시 Context API를 공유해야합니다.');
  }

  const { selected, setSelected, onChangeButton } = toggleContext;

  const isActive = selected === index;

  const handleClickButton = () => {
    onChangeButton(index);
    setSelected(index);
  };

  return (
    <Item $active={isActive} onClick={handleClickButton}>
      {children}
    </Item>
  );
};

ToggleGroup.button = ToggleButton;

export default ToggleGroup;
