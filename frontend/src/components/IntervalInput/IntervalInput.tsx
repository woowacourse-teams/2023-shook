import useIntervalInput from './hooks/useIntervalInput';
import {
  ErrorMessage,
  InputEnd,
  InputFlex,
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
      <InputFlex>
        <label htmlFor="start-min" />
        <InputStart
          id="start-min"
          name="minute"
          value={intervalStart.minute}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onFocus={onFocusIntervalStart}
          placeholder="0"
          autoComplete="off"
          active={activeInput === 'minute'}
        />
        <Separator>:</Separator>
        <label htmlFor="start-sec" />
        <InputStart
          id="start-sec"
          name="second"
          value={intervalStart.second}
          onChange={onChangeIntervalStart}
          onBlur={onBlurIntervalStart}
          onFocus={onFocusIntervalStart}
          placeholder="0"
          autoComplete="off"
          active={activeInput === 'second'}
        />
        <Separator> ~ </Separator>
        <InputEnd value={endMinute} disabled />
        <Separator inactive>:</Separator>
        <label htmlFor="start-sec" />
        <InputEnd value={endSecond} disabled />
      </InputFlex>
      <ErrorMessage role="alert">{errorMessage}</ErrorMessage>
    </IntervalContainer>
  );
};

export default IntervalInput;
