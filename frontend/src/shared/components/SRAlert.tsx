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
  bottom: -9999px;
  left: -9999px;

  overflow: hidden;

  width: 1px;
  height: 1px;
`;

export default SRAlert;
