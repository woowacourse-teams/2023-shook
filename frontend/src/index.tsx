import React from 'react';
import { createRoot } from 'react-dom/client';
import { RouterProvider } from 'react-router-dom';
import GlobalStyles from '@/styles/GlobalStyles';
import router from './router';

const root = createRoot(document.getElementById('root') as HTMLElement);

root.render(
  <React.StrictMode>
    <GlobalStyles />
    <RouterProvider router={router} />
  </React.StrictMode>
);
