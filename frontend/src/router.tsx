import { createBrowserRouter, Link } from 'react-router-dom';
import { VideoPlayerProvider } from './features/youtube/components/VideoPlayerProvider';
import AuthPage from './pages/AuthPage';
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import PartCollectingPage from './pages/PartCollectingPage';
import SongDetailPage from './pages/SongDetailPage';
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
        path: `${ROUTE_PATH.SONG_DETAIL}/:id`,
        element: (
          <VideoPlayerProvider>
            <SongDetailPage />
          </VideoPlayerProvider>
        ),
      },
      {
        path: `${ROUTE_PATH.MY_PAGE}/:id`,
        element: <MyPage />,
      },
    ],
  },
  {
    path: `${ROUTE_PATH.LOGIN_REDIRECT}`,
    element: <AuthPage />,
  },
  {
    path: '/test',
    element: (
      <Link
        to={`https://accounts.google.com/o/oauth2/v2/auth?scope=email&response_type=code&redirect_uri=http://localhost:8080${ROUTE_PATH.LOGIN_REDIRECT}&client_id=1008951336382-8o2n6n9u8jbj3sb6fe5jdeha9b6alnqa.apps.googleusercontent.com`}
      >
        사랑해요 코나안~
      </Link>
    ),
  },
]);

export default router;
