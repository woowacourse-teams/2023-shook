import '@testing-library/jest-dom';
import { screen } from '@testing-library/react';
import userEvent from '@testing-library/user-event';
import renderWithTheme from '@/utils/renderWithTheme';
import useModal from './hooks/useModal';
import Modal from './Modal';

describe('Modal 컴포넌트 테스트', () => {
  const RegistrationModal = () => {
    const { isOpen, openModal, closeModal } = useModal();

    return (
      <>
        <button onClick={openModal}>모달 열기</button>

        <Modal isOpen={isOpen} closeModal={closeModal}>
          <h2>킬링파트에 투표했습니다.</h2>
          <p>로고 색으로 좋아요!</p>
          <div>
            <button type="button" onClick={closeModal}>
              확인
            </button>
            <button type="button">공유하기</button>
          </div>
        </Modal>
      </>
    );
  };

  test('openModal로 모달을 열 수 있다.', async () => {
    renderWithTheme(<RegistrationModal />);

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();

    await userEvent.click(await screen.findByText<HTMLButtonElement>(`모달 열기`));

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).toBeInTheDocument();
  });

  test('closeModal로 모달을 닫을 수 있다.', async () => {
    renderWithTheme(<RegistrationModal />);

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();

    await userEvent.click(await screen.findByText<HTMLButtonElement>(`모달 열기`));

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).toBeInTheDocument();

    await userEvent.click(await screen.findByText<HTMLButtonElement>(`확인`));

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();
  });

  test('백드랍을 클릭하면 모달이 닫힌다.', async () => {
    renderWithTheme(<RegistrationModal />);

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();

    await userEvent.click(await screen.findByText<HTMLButtonElement>(`모달 열기`));

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).toBeInTheDocument();

    const backdrop = screen.getByRole('backdrop', { hidden: true });
    await userEvent.click(backdrop);

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();
  });

  test('esc를 누르면 모달이 닫힌다.', async () => {
    renderWithTheme(<RegistrationModal />);

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();

    await userEvent.click(await screen.findByText<HTMLButtonElement>(`모달 열기`));

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).toBeInTheDocument();

    await userEvent.keyboard('[Escape]');

    expect(screen.queryByText(/킬링파트에 투표했습니다./)).not.toBeInTheDocument();
  });
});
