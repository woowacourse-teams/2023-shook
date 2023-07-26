import React from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from '@/styles/GlobalStyles';
import ToastProvider from './components/@common/Toast/ToastProvider';
import router from './router';
import theme from './styles/theme';

const main = () => {
  const root = createRoot(document.getElementById('root') as HTMLElement);

  root.render(
    <React.StrictMode>
      <GlobalStyles />
      <ThemeProvider theme={theme}>
        <ToastProvider>
          <RouterProvider router={router} />
        </ToastProvider>
      </ThemeProvider>
    </React.StrictMode>
  );
};

const mswWork = async () => {
  import('./mocks/browser')
    .then(({ worker }) => {
      worker.start({
        serviceWorker: {
          url: `/mockServiceWorker.js`,
        },
      });
    })
    .then(() => {
      main();
    });

  main();
};

if (process.env.MODE === 'development') {
  mswWork();
}

if (process.env.MODE === 'production') {
  main();
}
