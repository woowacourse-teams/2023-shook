import { QueryClient, QueryClientProvider } from '@tanstack/react-query';
import { ReactQueryDevtools } from '@tanstack/react-query-devtools';
import React from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import { ThemeProvider } from 'styled-components';
import GlobalStyles from '@/shared/styles/GlobalStyles';
import AuthProvider from './features/auth/components/AuthProvider';
import { loadIFrameApi } from './features/youtube/remotes/loadIframeApi';
import router from './router';
import ToastProvider from './shared/components/Toast/ToastProvider';
import theme from './shared/styles/theme';

const queryClient = new QueryClient();

async function main() {
  if (process.env.NODE_ENV === 'development') {
    const { worker } = await import('./mocks/browser');

    await worker.start({
      onUnhandledRequest: 'bypass',
      serviceWorker: {
        url: '/mockServiceWorker.js',
      },
    });
  }

  await loadIFrameApi();

  const root = createRoot(document.getElementById('root') as HTMLElement);

  root.render(
    <React.StrictMode>
      <AuthProvider>
        <GlobalStyles />
        <ThemeProvider theme={theme}>
          <QueryClientProvider client={queryClient}>
            <ToastProvider>
              <RouterProvider router={router} />
            </ToastProvider>
            <ReactQueryDevtools />
          </QueryClientProvider>
        </ThemeProvider>
      </AuthProvider>
    </React.StrictMode>
  );
}

main();
