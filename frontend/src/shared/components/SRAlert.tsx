import { styled } from 'styled-components';

interface SRAlertProps {
  children: string;
}

const SRAlert = ({ children }: SRAlertProps) => {
  return (
    <Paragraph aria-live="assertive" aria-atomic="false">
      {children}
    </Paragraph>
  );
};

const Paragraph = styled.p`
  position: absolute;
  left: -9999px;
  bottom: -9999px;
  width: 1px;
  height: 1px;
  overflow: hidden;
`;

export default SRAlert;
