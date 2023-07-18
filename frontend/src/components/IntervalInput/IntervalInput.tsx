import useIntervalInput from './hooks/useIntervalInput';
import {
  ErrorMessage,
  InputEnd,
  Flex,
  InputStart,
  IntervalContainer,
  Separator,
} from './IntervalInput.style';
import type { IntervalInputProps } from './IntervalInput.type';

const IntervalInput = ({ songEnd }: IntervalInputProps) => {
  const {
    intervalStart,
    errorMessage,
    activeInput,
    endMinute,
    endSecond,
    onChangeIntervalStart,
    onFocusIntervalStart,
    onBlurIntervalStart,
  } = useIntervalInput(songEnd);

  return (
    <IntervalContainer>
      <Flex>
        <InputStart
          name="minute"
          value={intervalStart.minute}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onFocus={onFocusIntervalStart}
          placeholder="0"
          autoComplete="off"
          $active={activeInput === 'minute'}
        />
        <Separator>:</Separator>
        <InputStart
          name="second"
          value={intervalStart.second}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onFocus={onFocusIntervalStart}
          placeholder="0"
          autoComplete="off"
          $active={activeInput === 'second'}
        />
        <Separator> ~ </Separator>
        <InputEnd id="end-min" value={endMinute} disabled />
        <Separator $inactive>:</Separator>
        <InputEnd id="end-sec" value={endSecond} disabled />
      </Flex>
      <ErrorMessage role="alert">{errorMessage}</ErrorMessage>
    </IntervalContainer>
  );
};

export default IntervalInput;
