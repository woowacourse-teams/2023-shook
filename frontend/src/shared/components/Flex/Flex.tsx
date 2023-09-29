import styled, { css } from 'styled-components';
import type { BreakPoints } from '@/shared/types/theme';
import type { PropsWithChildren } from 'react';
import type { CSSProp } from 'styled-components';

interface FlexBox {
  $direction?: React.CSSProperties['flexDirection'];
  $wrap?: React.CSSProperties['flexWrap'];
  $gap?: number;
  $align?: React.CSSProperties['alignItems'];
  $justify?: React.CSSProperties['justifyContent'];
}

type ResponsiveFlexBox = Partial<Record<`$${BreakPoints}`, FlexBox>>;

type FlexProps<C extends React.ElementType> = {
  $as?: C;
  $css?: CSSProp;
} & FlexBox &
  ResponsiveFlexBox;

type Props<C extends React.ElementType> = PropsWithChildren<FlexProps<C>> &
  Omit<React.ComponentPropsWithoutRef<C>, keyof FlexProps<C>>;

const Flex = <C extends React.ElementType = 'div'>({ $as, children, ...props }: Props<C>) => {
  const tag = $as || 'div';

  return (
    <Container as={tag} {...props}>
      {children}
    </Container>
  );
};

export default Flex;

const Container = styled.div<Omit<FlexProps<'div'>, 'as'>>`
  display: flex;
  flex-direction: ${({ $direction }) => $direction ?? 'row'};
  flex-wrap: ${({ $wrap }) => $wrap ?? 'nowrap'};
  gap: ${({ $gap }) => $gap && `${$gap}px`};
  align-items: ${({ $align }) => $align ?? 'stretch'};
  justify-content: ${({ $justify }) => $justify ?? 'flex-start'};
  ${({ $css }) => $css}

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    ${({ $xxl }) => flexCss($xxl)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    ${({ $xl }) => flexCss($xl)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.lg}) {
    ${({ $lg }) => flexCss($lg)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    ${({ $md }) => flexCss($md)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.sm}) {
    ${({ $sm }) => flexCss($sm)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    ${({ $xs }) => flexCss($xs)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    ${({ $xxs }) => flexCss($xxs)}
  }
`;

const flexCss = (flexBox?: FlexBox) => {
  return css`
    flex-direction: ${flexBox?.$direction};
    flex-wrap: ${flexBox?.$wrap};
    gap: ${flexBox?.$gap && `${flexBox?.$gap}px`};
    align-items: ${flexBox?.$align};
    justify-content: ${flexBox?.$justify};
  `;
};
