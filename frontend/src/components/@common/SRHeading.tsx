import styled from 'styled-components';

interface SRHeadingProps {
  children: string;
}

const SRHeading = ({ children: pageName }: SRHeadingProps) => {
  return (
    <Heading tabIndex={1} aria-label={pageName}>
      {pageName}
    </Heading>
  );
};

export default SRHeading;

const Heading = styled.h1`
  position: absolute;
  left: -9999px;
  top: -9999px;
  width: 1px;
  height: 1px;
  overflow: hidden;
`;
