import { initialize, mswLoader } from 'msw-storybook-addon';
import type { Preview } from '@storybook/react';
import GlobalStyles from '../src/shared/styles/GlobalStyles';
import { ThemeProvider } from 'styled-components';
import theme from '../src/shared/styles/theme';
import { BrowserRouter } from 'react-router-dom';
import AuthProvider from '@/features/auth/components/AuthProvider';
import handlers from '@/mocks/handlers';

const customViewport = {
  xxl: {
    name: 'xxl',
    styles: {
      width: '1440px',
      height: '1080px',
    },
  },

  xl: {
    name: 'xl',
    styles: {
      width: '1280px',
      height: '720px',
    },
  },

  lg: {
    name: 'lg',
    styles: {
      width: '1024px',
      height: '720px',
    },
  },

  md: {
    name: 'md',
    styles: {
      width: '768px',
      height: '1024px',
    },
  },

  sm: {
    name: 'sm',
    styles: {
      width: '640px',
      height: '768px',
    },
  },

  xs: {
    name: 'xs',
    styles: {
      width: '420px',
      height: '768px',
    },
  },

  xxs: {
    name: 'xxs',
    styles: {
      width: '380px',
      height: '768px',
    },
  },
};

initialize();

const preview: Preview = {
  parameters: {
    actions: { argTypesRegex: '^on[A-Z].*' },
    viewport: { viewports: customViewport, defaultViewport: 'xs' },
    controls: {
      matchers: {
        color: /(background|color)$/i,
        date: /Date$/,
      },
    },
    msw: {
      handlers: [...handlers],
    },
  },
  loaders: [mswLoader],

  decorators: [
    (Story) => (
      <AuthProvider>
        <ThemeProvider theme={theme}>
          <BrowserRouter>
            <GlobalStyles />
            <Story />
          </BrowserRouter>
        </ThemeProvider>
      </AuthProvider>
    ),
  ],
};

export default preview;
