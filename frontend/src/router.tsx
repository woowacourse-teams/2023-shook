import { createBrowserRouter } from 'react-router-dom';
import Layout from './components/Layout/Layout';
import SongDetailPage from './pages/SongDetailPage';
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
    ],
  },
]);

export default router;
