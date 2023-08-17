import ROUTE_PATH from '@/shared/constants/path';

const redirectUrl = `${process.env.BASE_URL}${ROUTE_PATH.LOGIN_REDIRECT}`?.replace(/api\/?/, '');

const googleAuthUrl = `https://accounts.google.com/o/oauth2/v2/auth?scope=email&response_type=code&redirect_uri=${redirectUrl}&client_id=405219607197-qfpt1e3v1bm25ebvadt5bvttskse5vpg.apps.googleusercontent.com`;

export default googleAuthUrl;
