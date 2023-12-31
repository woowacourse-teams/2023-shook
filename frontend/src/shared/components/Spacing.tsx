import styled, { css } from 'styled-components';
import type { BreakPoints } from '@/shared/types/theme';

interface Spacing {
  direction: 'horizontal' | 'vertical';
  size: number;
}

interface ResponsiveSpacing extends Partial<Record<`$${BreakPoints}`, Partial<Spacing>>> {}

interface SpacingProps extends Spacing, ResponsiveSpacing {}

const Spacing = styled.div<SpacingProps>`
  flex: none;
  min-width: ${({ direction, size }) => (direction === 'horizontal' ? `${size}px` : '0')};
  min-height: ${({ direction, size }) => (direction === 'vertical' ? `${size}px` : '0')};

  @media (max-width: ${({ theme }) => theme.breakPoints.xxl}) {
    ${({ $xxl, direction, size }) => spacingCss($xxl, direction, size)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xl}) {
    ${({ $xl, direction, size }) => spacingCss($xl, direction, size)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.lg}) {
    ${({ $lg, direction, size }) => spacingCss($lg, direction, size)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.md}) {
    ${({ $md, direction, size }) => spacingCss($md, direction, size)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.sm}) {
    ${({ $sm, direction, size }) => spacingCss($sm, direction, size)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xs}) {
    ${({ $xs, direction, size }) => spacingCss($xs, direction, size)}
  }

  @media (max-width: ${({ theme }) => theme.breakPoints.xxs}) {
    ${({ $xxs, direction, size }) => spacingCss($xxs, direction, size)}
  }
`;

const spacingCss = (
  responsiveSpacing?: Partial<Spacing>,
  originalDir?: Spacing['direction'],
  originalSize?: Spacing['size']
) => {
  if (!responsiveSpacing) return;

  const direction = responsiveSpacing.direction ?? originalDir;
  const size = responsiveSpacing.size ?? originalSize;

  return css`
    min-width: ${direction === 'horizontal' ? `${size}px` : '0'};
    min-height: ${direction === 'vertical' ? `${size}px` : '0'};
  `;
};

export default Spacing;
