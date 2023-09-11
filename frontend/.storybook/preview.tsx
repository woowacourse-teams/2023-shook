import type { Preview } from '@storybook/react';
import GlobalStyles from '../src/shared/styles/GlobalStyles';
import { ThemeProvider } from 'styled-components';
import theme from '../src/shared/styles/theme';

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
  },
  decorators: [
    (Story) => (
      <ThemeProvider theme={theme}>
        <GlobalStyles />
        <Story />
      </ThemeProvider>
    ),
  ],
};

export default preview;
