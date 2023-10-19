import styled, { css } from 'styled-components';
import type { BreakPoints } from '@/shared/types/theme';
import type { CSSProp } from 'styled-components';

interface FlexBox {
  $direction?: React.CSSProperties['flexDirection'];
  $wrap?: React.CSSProperties['flexWrap'];
  $gap?: number;
  $align?: React.CSSProperties['alignItems'];
  $justify?: React.CSSProperties['justifyContent'];
  $css?: CSSProp;
}

interface ResponsiveFlexBox extends Partial<Record<`$${BreakPoints}`, FlexBox>> {}

interface FlexProps extends FlexBox, ResponsiveFlexBox {}

const Flex = styled.div<FlexProps>`
  display: flex;
  flex-direction: ${({ $direction = 'row' }) => $direction};
  flex-wrap: ${({ $wrap = 'nowrap' }) => $wrap};
  gap: ${({ $gap = 0 }) => `${$gap}px`};
  align-items: ${({ $align = 'stretch' }) => $align};
  justify-content: ${({ $justify = 'flex-start' }) => $justify};
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

export default Flex;

const flexCss = (flexBox?: FlexBox) => {
  if (!flexBox) return;
  const { $align, $direction, $gap, $justify, $wrap, $css } = flexBox;

  return css`
    flex-direction: ${$direction};
    flex-wrap: ${$wrap};
    gap: ${$gap && `${$gap}px`};
    align-items: ${$align};
    justify-content: ${$justify};
    ${$css}
  `;
};
