import { createContext, useContext } from 'react';

interface ToggleContextValue {
  selected: string | undefined;
  setSelected: React.Dispatch<React.SetStateAction<string | undefined>>;
  onChangeValue: (value: string) => void;
}

export const ToggleContext = createContext<ToggleContextValue | null>(null);

const useToggleContext = () => {
  const toggleContext = useContext(ToggleContext);

  if (!toggleContext) {
    throw new Error('toggle group 사용 시에 반드시 Context API를 공유해야합니다.');
  }

  return toggleContext;
};

export default useToggleContext;
