import { createBrowserRouter } from 'react-router-dom';
import { VideoPlayerProvider } from './features/youtube/components/VideoPlayerProvider';
import SongDetailPage from './pages/SongDetailPage';
import SongPage from './pages/SongPage';
import SongPopularPage from './pages/SongPopularPage';
import Layout from './shared/components/Layout/Layout';

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
        element: (
          <VideoPlayerProvider>
            <SongPage />
          </VideoPlayerProvider>
        ),
      },
    ],
  },
]);

export default router;
