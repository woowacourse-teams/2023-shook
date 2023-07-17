import { fireEvent, render, screen } from '@testing-library/react';
import IntervalInput from './IntervalInput';

describe('<IntervalInput /> 컴포넌트 테스트', () => {
  const songEnd = 210; // 3분 30초

  test('4개의 input이 모두 그려진다.', () => {
    render(<IntervalInput songEnd={songEnd} />);
    const startMin = screen.getAllByRole('textbox');

    expect(startMin).toHaveLength(4);
  });

  test('start 2분 10초면, end는 2분 20초다. ', () => {
    render(<IntervalInput songEnd={songEnd} />);
    const [startMin, startSec, endMin, endSec] = screen.getAllByRole<HTMLInputElement>('textbox');

    fireEvent.change(startMin, { target: { value: '2' } });
    fireEvent.change(startSec, { target: { value: '10' } });

    expect(endMin.value).toBe('2');
    expect(endSec.value).toBe('20');
  });

  test('start(4:10)가 노래(3:30)보다 길면 에러메세지를 띄운다.', async () => {
    render(<IntervalInput songEnd={songEnd} />);
    const [startMin, startSec, ,] = screen.getAllByRole<HTMLInputElement>('textbox');

    fireEvent.change(startMin, { target: { value: '4' } });
    fireEvent.change(startSec, { target: { value: '10' } });
    fireEvent.blur(startSec);

    expect(screen.findByText(/이하로 구간 시작점을 잡아주세요/)).toBeInTheDocument;
  });
});
