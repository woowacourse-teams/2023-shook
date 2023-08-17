import { createBrowserRouter, Link } from 'react-router-dom';
import googleAuthUrl from './features/auth/constants/googleAuthUrl';
import { VideoPlayerProvider } from './features/youtube/components/VideoPlayerProvider';
import AuthPage from './pages/AuthPage';
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import PartCollectingPage from './pages/PartCollectingPage';
import SongDetailPage from './pages/SongDetailPage';
import AuthLayout from './shared/components/Layout/AuthLayout';
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
        element: (
          <AuthLayout>
            <MyPage />
          </AuthLayout>
        ),
      },
    ],
  },
  {
    path: `${ROUTE_PATH.LOGIN_REDIRECT}`,
    element: <AuthPage />,
  },
  {
    path: '/test',
    element: <Link to={googleAuthUrl}>로그인 테스트</Link>,
  },
]);

export default router;
