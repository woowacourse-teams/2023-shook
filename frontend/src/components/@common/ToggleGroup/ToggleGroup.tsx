import { useState, useContext } from 'react';
import { ToggleContext } from './hooks/useToggleContext';
import { Container, Item } from './ToggleGroup.style';
import type { HtmlHTMLAttributes, PropsWithChildren } from 'react';

interface ToggleGroupProps extends PropsWithChildren {
  defaultIndex?: number;
  onChangeButton: (index: number) => void;
}

interface ToggleButtonProps extends HtmlHTMLAttributes<HTMLButtonElement> {
  index: number;
  children: string;
}

const ToggleGroup = ({ defaultIndex, onChangeButton, children }: ToggleGroupProps) => {
  const [selected, setSelected] = useState(defaultIndex);

  return (
    <ToggleContext.Provider value={{ selected, setSelected, onChangeButton }}>
      <Container>{children}</Container>
    </ToggleContext.Provider>
  );
};

const ToggleButton = ({ index, children, ...props }: ToggleButtonProps) => {
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
    <Item
      role="radio"
      aria-checked={isActive}
      $active={isActive}
      onClick={handleClickButton}
      {...props}
    >
      {children}
    </Item>
  );
};

ToggleGroup.Button = ToggleButton;

export default ToggleGroup;
