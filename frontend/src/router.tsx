import { createBrowserRouter } from 'react-router-dom';
import LoginPopupProvider from '@/features/auth/hooks/LoginPopUpContext';
import EditProfilePage from '@/pages/EditProfilePage';
import AuthPage from './pages/AuthPage';
import LoginPage from './pages/LoginPage';
import MainPage from './pages/MainPage';
import MyPage from './pages/MyPage';
import PartCollectingPage from './pages/PartCollectingPage';
import SearchResultPage from './pages/SearchResultPage';
import SingerDetailPage from './pages/SingerDetailPage';
import SongDetailListPage from './pages/SongDetailListPage';
import AuthLayout from './shared/components/Layout/AuthLayout';
import Layout from './shared/components/Layout/Layout';
import ROUTE_PATH from './shared/constants/path';

const router = createBrowserRouter([
  {
    path: ROUTE_PATH.ROOT,
    element: (
      <LoginPopupProvider>
        <Layout />
      </LoginPopupProvider>
    ),
    children: [
      {
        index: true,
        element: <MainPage />,
      },
      {
        path: `${ROUTE_PATH.COLLECT}/:id`,
        element: (
          <AuthLayout>
            <PartCollectingPage />
          </AuthLayout>
        ),
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
        path: `${ROUTE_PATH.EDIT_PROFILE}`,
        element: (
          <AuthLayout>
            <EditProfilePage />
          </AuthLayout>
        ),
      },
      {
        path: `${ROUTE_PATH.SEARCH_RESULT}`,
        element: <SearchResultPage />,
      },
      {
        path: `${ROUTE_PATH.SINGER_DETAIL}/:singerId`,
        element: <SingerDetailPage />,
      },
    ],
  },
  {
    path: `${ROUTE_PATH.LOGIN}`,
    element: <LoginPage />,
  },
  {
    path: `/:platform/redirect`,
    element: <AuthPage />,
  },
]);

export default router;
