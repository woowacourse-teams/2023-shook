import { createBrowserRouter } from 'react-router-dom';
import MainPage from './pages/MainPage';
import PartCollectingPage from './pages/PartCollectingPage';
import SongDetailListPage from './pages/SongDetailListPage';
import Layout from './shared/components/Layout/Layout';
import ROUTE_PATH from './shared/constants/path';

const router = createBrowserRouter([
  {
    path: ROUTE_PATH.ROOT,
    element: <Layout />,
    children: [
      {
        index: true,
        element: <MainPage />,
      },
      {
        path: `${ROUTE_PATH.COLLECT}/:id`,
        element: <PartCollectingPage />,
      },
      {
        path: `${ROUTE_PATH.SONG_DETAILS}/:id`,
        element: <SongDetailListPage />,
      },
    ],
  },
]);

export default router;
