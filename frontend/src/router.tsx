import { createBrowserRouter } from 'react-router-dom';
import Layout from './components/Layout/Layout';
import SongDetailPage from './pages/SongDetailPage';
import { SongPage } from './pages/SongPage';
import SongPopularPage from './pages/SongPopularPage/';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <SongPopularPage />,
      },
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
