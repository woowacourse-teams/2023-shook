import ROUTE_PATH from '@/shared/constants/path';

const redirectUrl = process.env.BASE_URL?.replace('api', '');

const googleAuthUrl = `https://accounts.google.com/o/oauth2/v2/auth?scope=email&response_type=code&redirect_uri=${redirectUrl}${ROUTE_PATH.LOGIN_REDIRECT}&client_id=1008951336382-8o2n6n9u8jbj3sb6fe5jdeha9b6alnqa.apps.googleusercontent.com`;

export default googleAuthUrl;
