import React from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from '@/styles/GlobalStyles';
import router from './router';
import theme from './styles/theme';

const root = createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <React.StrictMode>
    <GlobalStyles />
    <ThemeProvider theme={theme}>
      <RouterProvider router={router} />
    </ThemeProvider>
  </React.StrictMode>
);
