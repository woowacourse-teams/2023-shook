import path from '@/shared/constants/path';

const DOMAIN_URL = `${process.env.BASE_URL}`?.replace(/api\/?/, '');

export const googleAuthUrl = `https://accounts.google.com/o/oauth2/v2/auth?scope=email&response_type=code&redirect_uri=${DOMAIN_URL}${path.GOOGLE_REDIRECT}&client_id=405219607197-qfpt1e3v1bm25ebvadt5bvttskse5vpg.apps.googleusercontent.com`;
export const kakaoAuthUrl = `https://kauth.kakao.com/oauth/authorize?response_type=code&client_id=1808b8a2e3f29fbae5b54adc357a0692&redirect_uri=${DOMAIN_URL}${path.KAKAO_REDIRECT}`;

console.log('google url', googleAuthUrl);
console.log('kakao url', kakaoAuthUrl);
