import { render, screen } from '@testing-library/react';
import App from './App';

describe('App 테스트', () => {
  test('App render', () => {
    render(<App />);

    const elem = screen.getByText('App');
    expect(elem).toBeInTheDocument;
  });
});
