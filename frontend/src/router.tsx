import { createBrowserRouter } from 'react-router-dom';
import Layout from './components/Layout/Layout';
import SongDetailPage from './pages/SongDetailPage';
import SongPage from './pages/SongPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        path: '/:id',
        element: <SongDetailPage />,
      },
      {
        path: '/song/:id',
        element: <SongPage />,
      },
    ],
  },
]);

export default router;
