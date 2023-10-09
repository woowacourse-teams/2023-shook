import { createPortal } from 'react-dom';
import styled from 'styled-components';

const ResultSheet = () => {
  return createPortal(
    <SheetContainer>
      <SheetTitle>아티스트</SheetTitle>
      <TestList>
        <TestItem>1</TestItem>
        <TestItem>2</TestItem>
        <TestItem>3</TestItem>
      </TestList>
    </SheetContainer>,
    document.body
  );
};

export default ResultSheet;

const SheetContainer = styled.div`
  position: fixed;
  z-index: 2000;
  top: 70px;
  right: 12.33%;

  width: 400px;
  min-height: auto;

  color: white;

  background-color: #161f18;
  border-radius: 8px;

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    right: 8.33%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    right: 0%;
    width: 100%;
    min-height: 100%;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    top: 60px;
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    top: 50px;
  }
`;

const SheetTitle = styled.p``;
const TestList = styled.ul``;
const TestItem = styled.li``;
