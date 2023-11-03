import { createContext, useState } from 'react';
import { createPortal } from 'react-dom';
import { Flex } from 'shook-layout';
import styled from 'styled-components';
import type { ReactNode } from 'react';

const ConfirmContext = createContext<null | {
  getConfirmation: (modalState: ModalState) => Promise<boolean>;
}>(null);

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
  cancelName?: string;
  confirmName?: string;
}

type Resolver<T> = (value: T) => void;

const createPromise = <T,>() => {
  let resolver: Resolver<T> | null = null;
  const promise = new Promise<T>((resolve) => {
    resolver = resolve;
  });

  return { promise, resolver };
};

const ConfirmProvider = (children: ReactNode) => {
  const [isOpen, setIsOpen] = useState(false);
  const [resolve, setResolve] = useState<((value: boolean) => void) | null>(null);
  const [modalState, setModalState] = useState<ModalState>({
    title: '',
    content: '',
    cancelName: '닫기',
    confirmName: '확인',
  });
  const { title, content, cancelName, confirmName } = modalState;

  const getConfirmation = ({
    cancelName = '닫기',
    confirmName = '확인',
    ...restState
  }: ModalState): Promise<boolean> => {
    const { promise, resolver } = createPromise<boolean>();
    setResolve(resolver);
    setModalState({
      cancelName,
      confirmName,
      ...restState,
    });
    setIsOpen(true);

    return promise;
  };

  const onClick = (status: boolean) => {
    setIsOpen(false);

    if (resolve) {
      resolve(status);
    }
  };

  return (
    <ConfirmContext.Provider value={{ getConfirmation }}>
      {children}
      {isOpen &&
        createPortal(
          <>
            <Backdrop />
            <ContainerFlex>
              <Title>{title}</Title>
              <Content>{content}</Content>
              <ButtonFlex>
                <CancelButton onClick={() => onClick(false)}>{cancelName}</CancelButton>
                <ConfirmButton onClick={() => onClick(true)}>{confirmName}</ConfirmButton>
              </ButtonFlex>
            </ContainerFlex>
          </>,
          document.body
        )}
    </ConfirmContext.Provider>
  );
};

export default ConfirmProvider;
