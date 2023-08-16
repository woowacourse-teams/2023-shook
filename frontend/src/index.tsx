import React from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from '@/shared/styles/GlobalStyles';
import AuthProvider from './features/auth/components/AuthProvider';
import router from './router';
import ToastProvider from './shared/components/Toast/ToastProvider';
import theme from './shared/styles/theme';

async function main() {
  if (process.env.NODE_ENV === 'development') {
    const { worker } = await import('./mocks/browser');

    await worker.start({
      serviceWorker: {
        url: '/mockServiceWorker.js',
      },
    });
  }

  // TODO: 웹 사이트 진입 시에 자동 로그인 (token 확인)

  const root = createRoot(document.getElementById('root') as HTMLElement);

  root.render(
    <React.StrictMode>
      <AuthProvider>
        <GlobalStyles />
        <ThemeProvider theme={theme}>
          <ToastProvider>
            <RouterProvider router={router} />
          </ToastProvider>
        </ThemeProvider>
      </AuthProvider>
    </React.StrictMode>
  );
}

main();
