import styled from 'styled-components';
import type { PropsWithChildren } from 'react';

type FlexProps<C extends React.ElementType> = {
  as?: C;
  direction?: React.CSSProperties['flexDirection'];
  wrap?: React.CSSProperties['flexWrap'];
  gap?: number;
  align?: React.CSSProperties['alignItems'];
  justify?: React.CSSProperties['justifyContent'];
};

type Props<C extends React.ElementType> = PropsWithChildren<FlexProps<C>> &
  Omit<React.ComponentPropsWithoutRef<C>, keyof FlexProps<C>>;

const Flex = <C extends React.ElementType = 'div'>({ as, children, ...restProps }: Props<C>) => {
  const tag = as || 'div';

  return (
    <Container as={tag} {...restProps}>
      {children}
    </Container>
  );
};

export default Flex;

const Container = styled.div<Omit<FlexProps<'div'>, 'as'>>`
  display: flex;
  flex-direction: ${({ direction }) => direction || 'row'};
  flex-wrap: ${({ wrap }) => wrap || 'nowrap'};
  gap: ${({ gap }) => (gap ? `${gap}px` : '0')};
  align-items: ${({ align }) => align || 'stretch'};
  justify-content: ${({ justify }) => justify || 'flex-start'};
`;
