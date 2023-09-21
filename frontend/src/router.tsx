import { createBrowserRouter } from 'react-router-dom';
import EditProfilePage from '@/pages/EditProfilePage';
import AuthPage from './pages/AuthPage';
import LoginPage from './pages/LoginPage';
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import PartCollectingPage from './pages/PartCollectingPage';
import SongDetailListPage from './pages/SongDetailListPage';
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
        path: `${ROUTE_PATH.SONG_DETAILS}/:id/:genre`,
        element: <SongDetailListPage />,
      },
      {
        path: `${ROUTE_PATH.MY_PAGE}`,
        element: (
          <AuthLayout>
            <MyPage />
          </AuthLayout>
        ),
      },
      {
        path: `${ROUTE_PATH.MY_PAGE}`,
        element: <MyPage />,
      },
      {
        path: `${ROUTE_PATH.EDIT_PROFILE}`,
        element: <EditProfilePage />,
      },
    ],
  },
  {
    path: `${ROUTE_PATH.LOGIN}`,
    element: <LoginPage />,
  },
  {
    path: `${ROUTE_PATH.LOGIN_REDIRECT}`,
    element: <AuthPage />,
  },
]);

export default router;
