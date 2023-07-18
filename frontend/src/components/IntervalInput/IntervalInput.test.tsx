import { fireEvent, render, screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import IntervalInput from './IntervalInput';

describe('<IntervalInput /> 컴포넌트 테스트', () => {
  const songEnd = 210; // 3분 30초

  test('4개의 input이 모두 그려진다.', () => {
    render(<IntervalInput songEnd={songEnd} />);
    const inputs = screen.getAllByRole('textbox');

    expect(inputs).toHaveLength(4);
  });

  test('start 2분 10초면, end는 2분 20초다. ', () => {
    render(<IntervalInput songEnd={songEnd} />);
    const [startMin, startSec, endMin, endSec] = screen.getAllByRole<HTMLInputElement>('textbox');

    fireEvent.change(startMin, { target: { value: '2' } });
    fireEvent.change(startSec, { target: { value: '10' } });

    expect(endMin.value).toBe('2');
    expect(endSec.value).toBe('20');
  });

  test('start 1분 55초면, end는 2분 5초다. ', () => {
    render(<IntervalInput songEnd={songEnd} />);
    const [startMin, startSec, endMin, endSec] = screen.getAllByRole<HTMLInputElement>('textbox');

    fireEvent.change(startMin, { target: { value: '1' } });
    fireEvent.change(startSec, { target: { value: '55' } });

    expect(endMin.value).toBe('2');
    expect(endSec.value).toBe('5');
  });

  test('start(3:25)가 노래길이에서 구간길이를 뺀 것(3:20)보다 길면 에러메세지를 띄운다.', async () => {
    render(<IntervalInput songEnd={songEnd} />);
    const [startMin, startSec] = screen.getAllByRole<HTMLInputElement>('textbox').slice(0, 2);

    await userEvent.type(startMin, '3');
    await userEvent.type(startSec, '25');
    fireEvent.blur(startSec);

    const errorMessage = screen.getByRole('alert');

    expect(errorMessage.textContent).toBeTruthy();
  });
});
