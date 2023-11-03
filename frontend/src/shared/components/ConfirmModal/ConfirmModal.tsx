import { createContext, useState } from 'react';
import { createPortal } from 'react-dom';
import { Flex } from 'shook-layout';
import styled from 'styled-components';
import type { ReactNode } from 'react';

const ConfirmContext = createContext<null>(null);

const Backdrop = styled.div``;
const ContainerFlex = styled(Flex)``;
const ButtonFlex = styled(Flex)``;
const Title = styled.header``;
const Content = styled.div``;
const CancelButton = styled.button``;
const ConfirmButton = styled.button``;

interface ModalState {
  title: string;
  content: ReactNode;
  cancelName: string;
  confirmName: string;
}

const ConfirmProvider = (children: ReactNode) => {
  const [isOpen, setIsOpen] = useState(false);
  const [modalState, setModalState] = useState<ModalState>({
    title: '',
    content: '',
    cancelName: '닫기',
    confirmName: '확인',
  });
  const { title, content, cancelName, confirmName } = modalState;

  return (
    <ConfirmContext.Provider value={null}>
      {children}
      {isOpen &&
        createPortal(
          <>
            <Backdrop />
            <ContainerFlex>
              <Title>{title}</Title>
              <Content>{content}</Content>
              <ButtonFlex>
                <CancelButton>{cancelName}</CancelButton>
                <ConfirmButton>{confirmName}</ConfirmButton>
              </ButtonFlex>
            </ContainerFlex>
          </>,
          document.body
        )}
    </ConfirmContext.Provider>
  );
};

export default ConfirmProvider;
