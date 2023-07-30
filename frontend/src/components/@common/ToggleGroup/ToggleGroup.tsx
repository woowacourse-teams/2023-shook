import { useState, createContext, useContext } from 'react';
import { styled } from 'styled-components';
import type React from 'react';
import type { PropsWithChildren } from 'react';

interface ToggleGroupProps extends PropsWithChildren {
  defaultValue?: string;
  onChangeValue: (value: string) => void;
}

interface ToggleButtonProps extends PropsWithChildren {
  value: string;
}

interface ToggleContextValue {
  selected: string | undefined;
  setSelected: React.Dispatch<React.SetStateAction<string | undefined>>;
  onChangeValue: (value: string) => void;
}

const ToggleContext = createContext<ToggleContextValue | null>(null);

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

export const Container = styled.div`
  display: flex;
  justify-content: center;
  width: 100%;
  gap: 20px;
`;

export const Item = styled.button<{ $active: boolean }>`
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
