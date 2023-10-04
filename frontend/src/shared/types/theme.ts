import type theme from '@/shared/styles/theme';

export type ThemeType = typeof theme;
export type BreakPoints = keyof typeof theme.breakPoints;
