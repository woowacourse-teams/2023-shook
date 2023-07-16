import { createBrowserRouter } from 'react-router-dom';
import Layout from './components/Layout/Layout';
import SongDetailPage from './pages/SongDetailPage';

const router = createBrowserRouter([
  {
    path: '/',
    element: <Layout />,
    children: [
      {
        index: true,
        element: <SongDetailPage />,
      },
    ],
  },
]);

export default router;
